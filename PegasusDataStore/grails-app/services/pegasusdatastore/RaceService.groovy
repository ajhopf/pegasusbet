package pegasusdatastore

import exceptions.ResourceNotFoundException
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import model.dtos.RaceCourseDTO
import model.dtos.raceDTOs.HorseJockeyDTO
import model.dtos.raceDTOs.RaceRequestDTO
import model.dtos.raceDTOs.RaceResponseDTO
import model.mappers.RaceCourseMapper
import model.mappers.RaceMapper
import pegasusdatastore.interfaces.IRaceService

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service(Race)
abstract class RaceService implements IRaceService {
    OddsService oddsService

    @Transactional
    RaceResponseDTO getRaceByRaceHorseJockeyId(Long id) {
        RaceHorseJockey raceHorseJockey = RaceHorseJockey.get(id)

        if(!raceHorseJockey) {
            throw new ResourceNotFoundException("Não foi possível encontrar o id $id")
        }

        return RaceMapper.toResponseDTO(raceHorseJockey.race)
    }

    @Override
    @Transactional
    Race save(RaceRequestDTO raceRequestDTO) throws ResourceNotFoundException, DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        LocalDate date = LocalDate.parse(raceRequestDTO.date, dateFormatter)
        LocalTime time = LocalTime.parse(raceRequestDTO.time, timeFormatter)

        RaceCourse raceCourse = RaceCourse.get(raceRequestDTO.raceCourseId)

        if (!raceCourse) {
            throw new ResourceNotFoundException("RaceCourse not found. Id: ${raceRequestDTO.raceCourseId}")
        }

        Race race = new Race(
                raceCourse: raceCourse,
                date: date,
                time: time
        )

        race = race.save(flush: true)

        List<RaceHorseJockey> raceHorseJockeyList = saveHorseJockeys(raceRequestDTO.horseJockeys, race)
        oddsService.calculateInitialOdds(raceHorseJockeyList)

        return race
    }

    @Override
    @Transactional
    List<RaceResponseDTO> list(Map params) {
        List<Race> raceList = Race.list(offset: params.offset, max: params.max)
        return RaceMapper.toDTOs(raceList)
    }

    @Transactional
    List<RaceHorseJockey> saveHorseJockeys(List<HorseJockeyDTO> horseJockeyRequestDTOList, Race race) {
        List<RaceHorseJockey> raceHorseJockeyList = []

        horseJockeyRequestDTOList.each {horseJockeyRequestDTO ->
            Horse horse = Horse.get(horseJockeyRequestDTO.horseId)

            if (!horse) {
                throw new ResourceNotFoundException("Horse not found. Id: ${horseJockeyRequestDTO.horseId}")
            }

            Jockey jockey = Jockey.get(horseJockeyRequestDTO.jockeyId)

            if (!jockey) {
                throw new ResourceNotFoundException("Jockey not found. Id: ${horseJockeyRequestDTO.horseId}")
            }

            RaceHorseJockey raceHorseJockey = new RaceHorseJockey(
                    number: horseJockeyRequestDTO.number,
                    horse: horse,
                    jockey: jockey,
                    race: race,
                    raceTime: null,
                    position: null
            )

            RaceHorseJockey savedRaceHorseJockey = raceHorseJockey.save(flush: true)
            raceHorseJockeyList << savedRaceHorseJockey
        }

        return raceHorseJockeyList
    }
}
