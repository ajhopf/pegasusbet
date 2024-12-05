package model.mappers

import model.dtos.JockeyDTO
import pegasusdatastore.Jockey

class JockeyMapper {
    static JockeyDTO toDTO(Jockey jockey) {
        return new JockeyDTO(
                id: jockey.id,
                name: jockey.name,
                numberOfRaces: jockey.numberOfRaces,
                numberOfVictories: jockey.numberOfVictories,
                lastResults: jockey.lastResults
        )
    }

    static List<JockeyDTO> toDTOs(List<Jockey> jockey) {
        return jockey.collect { toDTO(it) }
    }

}
