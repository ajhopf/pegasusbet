package model.mappers

import model.dtos.jockeyDTOs.JockeyResultResponseDTO
import model.dtos.raceDTOs.RaceHorseJockeyDTO
import model.dtos.raceDTOs.RaceResponseDTO
import pegasusdatastore.Race

class RaceMapper {
    static RaceResponseDTO toResponseDTO(Race race) {
        return new RaceResponseDTO(
                id: race.id,
                raceCourseId: race.raceCourse.id,
                time: race.time,
                date: race.date,
                raceHorseJockeys: race.raceHorseJockey ? race.raceHorseJockey.collect { new RaceHorseJockeyDTO(it) } : []
        )
    }

    static List<RaceResponseDTO> toDTOs(List<Race> races) {
        return races.collect { toResponseDTO(it) }
    }

}
