package model.mappers

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
                raceHorseJockeys: race.raceHorseJockey?.collect { raceHorseJockey ->
                    [
                            horseId: raceHorseJockey.horse.id,
                            jockeyId: raceHorseJockey.jockey.id,
                            position: raceHorseJockey.position,
                            raceTime: raceHorseJockey.raceTime?.toString()
                    ]
                } as List<RaceHorseJockeyDTO>
        )
    }

    static List<RaceResponseDTO> toDTOs(List<Race> races) {
        return races.collect { toResponseDTO(it) }
    }

}
