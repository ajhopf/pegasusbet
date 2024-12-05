package model.mappers

import model.dtos.HorseDTO
import pegasusdatastore.Horse

class HorseMapper {
    static HorseDTO toDTO(Horse horse) {
        return new HorseDTO(
                id: horse.id,
                name: horse.name,
                age: horse.age,
                sex: horse.sex,
                numberOfRaces: horse.numberOfRaces,
                numberOfVictories: horse.numberOfVictories,
                lastResults: horse.lastResults
        )
    }

    static List<HorseDTO> toDTOs(List<Horse> horses) {
        return horses.collect { toDTO(it) }
    }

}
