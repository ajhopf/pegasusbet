package pegasusdatastore

import dao.HorseDAO
import dao.JockeyDAO
import dao.RaceCourseDAO
import dao.RaceDAO
import dao.RaceHorseJockeyDAO
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import java.time.LocalDate

class RaceServiceTest extends Specification implements ServiceUnitTest<RaceService> {
    RaceDAO raceDAO = Mock()
    RaceHorseJockeyDAO raceHorseJockeyDAO = Mock()
    HorseDAO horseDAO = Mock()
    JockeyDAO jockeyDAO = Mock()
    RaceCourseDAO raceCourseDAO = Mock()
    OddsService oddsService = Mock()

    Race race = null
    RaceHorseJockey raceHorseJockey = null
    Horse horse = null
    Jockey jockey = null

    def setup() {
        service.raceDAO = raceDAO
        service.raceHorseJockeyDAO = raceHorseJockeyDAO
        service.horseDAO = horseDAO
        service.jockeyDAO = jockeyDAO
        service.raceCourseDAO = raceCourseDAO
        service.oddsService = oddsService

        this.race = new Race(
                date: LocalDate.of(2025, 3, 1)
        )
        this.horse = new Horse(id: 1, numberOfVictories: 0, numberOfRaces: 0)
        this.jockey = new Jockey(id: 2, numberOfVictories: 0, numberOfRaces: 0)

        this.raceHorseJockey = new RaceHorseJockey(
                horse: this.horse,
                jockey: this.jockey
        )

    }

    def "addResultsToRace desserializa a string e adiciona os resultados"() {
        given: "Uma string de resultados"
        String resultsString = '{"raceId":44815,"positions":[{"raceHorseJockeyId":44816,"position":5.432071770964975,"odds":2.5,"result":"1/2"}]}'
        and: "Uma corrida, um raceHorseJockey, um cavalo e um jockey"
        raceDAO.getRaceById(_) >> this.race
        raceHorseJockeyDAO.getRaceHorseJockeyById(_) >> this.raceHorseJockey
        horseDAO.getHorseById(_) >> this.horse
        jockeyDAO.getJockeyById(_) >> this.jockey

        when: "método é invocado"
        service.addResultsToRace(resultsString)

        then: "corrida está finalizada"
        this.race.finished
        and: "resultados foram adicionados ao cavalo e o jockey"
        this.horse.numberOfVictories == 1
        this.horse.numberOfRaces == 1
        this.jockey.numberOfRaces == 1
        this.jockey.numberOfVictories == 1
        and: "colocação final foi adicionada ao raceHorseJockey"
        this.raceHorseJockey.position == "1/2"
    }

}
