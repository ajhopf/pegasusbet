package model.mappers

import model.dtos.horseDTOs.HorseRequestDTO
import model.dtos.horseDTOs.HorseResponseDTO
import model.dtos.horseDTOs.HorseResultResponseDTO
import pegasusdatastore.Horse

class HorseMapper {
    static HorseResponseDTO toResponseDTO(Horse horse) {
        return new HorseResponseDTO(
                id: horse.id,
                name: horse.name,
                age: horse.age,
                sex: horse.sex,
                numberOfRaces: horse.numberOfRaces,
                numberOfVictories: horse.numberOfVictories,
                horseResults: horse.horseResults ? horse.horseResults.collect { new HorseResultResponseDTO(it) } : []
        )
    }

    static List<HorseResponseDTO> toDTOs(List<Horse> horses) {
        if (!horses) {
            return []
        }
        return horses.collect { toResponseDTO(it) }
    }

    static Horse fromDTO(HorseRequestDTO horseRequestDTO) {
        return new Horse(
                name: horseRequestDTO.name,
                age: horseRequestDTO.age,
                sex: horseRequestDTO.sex,
                numberOfRaces: horseRequestDTO.numberOfRaces,
                numberOfVictories: horseRequestDTO.numberOfVictories,
                horseResults: horseRequestDTO.horseResults
        )
    }

}
