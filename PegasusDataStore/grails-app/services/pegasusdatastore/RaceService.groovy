package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import dao.HorseDAO
import dao.JockeyDAO
import dao.RaceCourseDAO
import dao.RaceDAO
import dao.RaceHorseJockeyDAO
import exceptions.ResourceNotFoundException
import grails.gorm.transactions.Transactional
import model.dtos.raceDTOs.HorseJockeyDTO
import model.dtos.raceDTOs.RaceHorseJockeyDTO
import model.dtos.raceDTOs.RaceRequestDTO
import model.dtos.raceDTOs.RaceResponseDTO
import model.dtos.raceDTOs.RaceResultPositions
import model.mappers.RaceMapper

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Transactional
class RaceService {
    RaceDAO raceDAO
    RaceHorseJockeyDAO raceHorseJockeyDAO
    HorseDAO horseDAO
    JockeyDAO jockeyDAO
    RaceCourseDAO raceCourseDAO

    OddsService oddsService

    void addResultsToRace(String resultsString) {
        println resultsString
        ObjectMapper objectMapper = new ObjectMapper()
        Map<String, Object> results = objectMapper.readValue(resultsString, Map.class)

        Long raceId = results.raceId as Long
        List<RaceResultPositions> positions = results.positions as RaceResultPositions[]

        Race race = raceDAO.getRaceById(raceId)

        positions.each {p ->
            Long raceHorseJockeyId = p.raceHorseJockeyId

            RaceHorseJockey rhj = raceHorseJockeyDAO.getRaceHorseJockeyById(raceHorseJockeyId)

            addResultsToHorseAndJockey(rhj, race, p.result)

            rhj.position = p.result

            raceHorseJockeyDAO.saveRaceHorseJockey(rhj)
        }


        race.finished = true

        raceDAO.saveRace(race)
    }

    void addResultsToHorseAndJockey(RaceHorseJockey rhj, Race race, String result) {
        Horse horse = horseDAO.getHorseById(rhj.horseId)

        HorseResults horseResult = new HorseResults(
                horse: horse,
                date: race.date,
                result: result
        )

        horseDAO.saveHorseResults(horseResult)

        Jockey jockey = jockeyDAO.getJockeyById(rhj.jockeyId)

        JockeyResults jockeyResults = new JockeyResults(
                jockey: jockey,
                date: race.date,
                result: result
        )

        jockeyDAO.saveJockeyResults(jockeyResults)

        jockey.numberOfRaces = jockey.numberOfRaces + 1
        horse.numberOfRaces = horse.numberOfRaces + 1

        if (result.split("/")[0] == "1") {
            jockey.numberOfVictories = jockey.numberOfVictories + 1
            horse.numberOfVictories = horse.numberOfVictories + 1
        }

        jockeyDAO.saveJockey(jockey)
        horseDAO.saveHorse(horse)
    }

    RaceResponseDTO getRaceByRaceHorseJockeyId(Long id) {
        RaceHorseJockey raceHorseJockey = raceHorseJockeyDAO.getRaceHorseJockeyById(id)

        if(!raceHorseJockey) {
            throw new ResourceNotFoundException("Não foi possível encontrar o id $id")
        }

        return RaceMapper.toResponseDTO(raceHorseJockey.race)
    }

    RaceHorseJockeyDTO increaseRaceHorseJockeyTotalBetsAmount(String newBetString) {
        ObjectMapper objectMapper = new ObjectMapper()

        Map<String, Object> betdata = objectMapper.readValue(newBetString, Map.class)

        Long id = betdata.raceHorseJockeyId as Long
        Long amount = betdata.amount as Long

        RaceHorseJockey raceHorseJockey = raceHorseJockeyDAO.getRaceHorseJockeyById(id)

        raceHorseJockey.totalBetsAmount += amount

        raceHorseJockeyDAO.saveRaceHorseJockey(raceHorseJockey)

        return new RaceHorseJockeyDTO(raceHorseJockey)
    }

    Race save(RaceRequestDTO raceRequestDTO) throws ResourceNotFoundException, DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        LocalDate date = LocalDate.parse(raceRequestDTO.date, dateFormatter)
        LocalTime time = LocalTime.parse(raceRequestDTO.time, timeFormatter)

        RaceCourse raceCourse = raceCourseDAO.getRaceCourseById(raceRequestDTO.raceCourseId)

        if (!raceCourse) {
            throw new ResourceNotFoundException("RaceCourse not found. Id: ${raceRequestDTO.raceCourseId}")
        }

        Race race = new Race(
                raceCourse: raceCourse,
                date: date,
                time: time,
                finished: false
        )

        race = raceDAO.saveRace(race)

        List<RaceHorseJockey> raceHorseJockeyList = saveHorseJockeys(raceRequestDTO.horseJockeys, race)
        oddsService.calculateInitialOdds(raceHorseJockeyList)

        return race
    }

    List<RaceResponseDTO> list(Map params) {
//        List<Race> raceList = Race.list(offset: params.offset, max: params.max)
        List<Race> raceList = raceDAO.listRaces(params)
        return RaceMapper.toDTOs(raceList)
    }

    List<RaceHorseJockey> saveHorseJockeys(List<HorseJockeyDTO> horseJockeyRequestDTOList, Race race) {
        List<RaceHorseJockey> raceHorseJockeyList = []

        horseJockeyRequestDTOList.each {horseJockeyRequestDTO ->
            Horse horse = horseDAO.getHorseById(horseJockeyRequestDTO.horseId)

            if (!horse) {
                throw new ResourceNotFoundException("Horse not found. Id: ${horseJockeyRequestDTO.horseId}")
            }

            Jockey jockey = jockeyDAO.getJockeyById(horseJockeyRequestDTO.jockeyId)

            if (!jockey) {
                throw new ResourceNotFoundException("Jockey not found. Id: ${horseJockeyRequestDTO.horseId}")
            }

            RaceHorseJockey raceHorseJockey = new RaceHorseJockey(
                    number: horseJockeyRequestDTO.number,
                    horse: horse,
                    jockey: jockey,
                    race: race,
                    totalBetsAmount: 0,
                    raceTime: null,
                    position: null
            )

            RaceHorseJockey savedRaceHorseJockey = raceHorseJockeyDAO.saveRaceHorseJockey(raceHorseJockey)
            raceHorseJockeyList << savedRaceHorseJockey
        }

        return raceHorseJockeyList
    }
}
