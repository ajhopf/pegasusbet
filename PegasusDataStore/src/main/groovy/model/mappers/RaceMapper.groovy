package model.mappers

import model.dtos.raceDTOs.RaceHorseJockeyDTO
import model.dtos.raceDTOs.RaceResponseDTO
import pegasusdatastore.Race

class RaceMapper {
    static RaceResponseDTO toResponseDTO(Race race) {
        return new RaceResponseDTO(
                id: race.id,
                raceCourse: RaceCourseMapper.toDTO(race.raceCourse),
                time: race.time,
                date: race.date,
                finished: race.finished,
                raceHorseJockeys: race.raceHorseJockey ? race.raceHorseJockey.collect { new RaceHorseJockeyDTO(it) } : []
        )
    }

    static List<RaceResponseDTO> toDTOs(List<Race> races) {
        return races.collect { toResponseDTO(it) }
    }

}
