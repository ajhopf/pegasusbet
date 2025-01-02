package pegasusdatastore

import dao.OddsDAO
import dao.RaceHorseJockeyDAO
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class OddsServiceTest extends Specification implements ServiceUnitTest<OddsService>, DataTest {
    OddsDAO oddsDAO = Mock()
    RaceHorseJockeyDAO raceHorseJockeyDAO = Mock()

    Horse goodHorse = null
    Horse badHorse = null

    def setup() {
        service.oddsDAO = oddsDAO
        service.raceHorseJockeyDAO = raceHorseJockeyDAO

        this.goodHorse = new Horse(
                id: 1,
                name: "good horse",
                numberOfVictories: 1,
                numberOfRaces: 1,
                horseResults: [new HorseResults(horse: this.goodHorse, result: "1/10")]
        )

        this.badHorse = new Horse(
                id: 2,
                name: "bad horse",
                numberOfVictories: 0,
                numberOfRaces: 1,
                horseResults: [new HorseResults(horse: this.badHorse, result: "10/10")]
        )
    }

    def "calculateHorseRating de um cavalo com piores resultados deve ser menor que de um cavalo com melhores resultados"() {
        when:
        Double goodHorseRating = service.calculateHorseRating(this.goodHorse)
        Double badHorseRating = service.calculateHorseRating(this.badHorse)

        then:
        goodHorseRating > badHorseRating
    }

    def "calculateAveragePosition calcula a média dos resultados válidos mesmo se houver resultado inválido"() {
        given: "Um cavalo com vários resultados e um resultado inválido"
        HorseResults result1 = new HorseResults(horse: this.goodHorse, result: "1/10")
        HorseResults result2 = new HorseResults(horse: this.goodHorse, result: "opa assim nao funciona")
        HorseResults result3 = new HorseResults(horse: this.goodHorse, result: "1/10")
        HorseResults result4 = new HorseResults(horse: this.goodHorse, result: "1/10")
        this.goodHorse.horseResults = [result1, result2, result3, result4]

        when:
        Double averagePosition = service.calculateAveragePosition(this.goodHorse)

        then:
        averagePosition == 1
    }

    def "calculateInitialOdds ordena cavalos corretamente e atribui as odds corretas"() {
        given: "Três racehorsejockeys"
        Horse horse2 = new Horse(
                id: 2,
                name: "second horse",
                numberOfVictories: 1,
                numberOfRaces: 2,
                horseResults: [new HorseResults(horse: this.goodHorse, result: "1/10"), new HorseResults(horse: this.goodHorse, result: "2/10")]
        )
        Jockey jockey = new Jockey()
        RaceHorseJockey raceHorseJockey1 = new RaceHorseJockey(id: 1, horse: this.goodHorse, jockey: jockey)
        raceHorseJockey1.id = 1
        RaceHorseJockey raceHorseJockey2 = new RaceHorseJockey(id: 2, horse: horse2, jockey: jockey)
        raceHorseJockey2.id = 2
        RaceHorseJockey raceHorseJockey3 = new RaceHorseJockey(id: 3, horse: this.badHorse, jockey: jockey)
        raceHorseJockey3.id = 3
        and: "Mock de acesso ao banco de dados"
        raceHorseJockeyDAO.getRaceHorseJockeyById(1) >> raceHorseJockey1
        raceHorseJockeyDAO.getRaceHorseJockeyById(2) >> raceHorseJockey2
        raceHorseJockeyDAO.getRaceHorseJockeyById(3) >> raceHorseJockey3

        when: "método é invocado"
        Map<Long, Double> oddsMap = service.calculateInitialOdds([raceHorseJockey3, raceHorseJockey1, raceHorseJockey2])

        then:
        oddsMap[1L] == 2.0
        oddsMap[2L] == 2.5
        oddsMap[3L] == 3.0
    }

}
