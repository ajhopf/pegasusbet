package model.mappers

import model.dtos.jockeyDTOs.JockeyRequestDTO
import model.dtos.jockeyDTOs.JockeyResponseDTO
import model.dtos.jockeyDTOs.JockeyResultResponseDTO
import pegasusdatastore.Jockey

class JockeyMapper {
    static JockeyResponseDTO toResponseDTO(Jockey jockey) {
        return new JockeyResponseDTO(
                id: jockey.id,
                name: jockey.name,
                numberOfRaces: jockey.numberOfRaces,
                numberOfVictories: jockey.numberOfVictories,
                jockeyResults: jockey.jockeyResults ? jockey.jockeyResults.collect { new JockeyResultResponseDTO(it) } : []
        )
    }

    static List<JockeyResponseDTO> toDTOs(List<Jockey> jockey) {
        return jockey.collect { toResponseDTO(it) }
    }

    static Jockey fromDTO(JockeyRequestDTO jockeyRequestDTO) {
        return new Jockey(
                name: jockeyRequestDTO.name,
                numberOfRaces: jockeyRequestDTO.numberOfRaces,
                numberOfVictories: jockeyRequestDTO.numberOfVictories,
                jockeyResults: jockeyRequestDTO.jockeyResults
        )
    }
}
