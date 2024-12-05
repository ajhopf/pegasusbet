package model.dtos

import pegasusdatastore.Horse
import pegasusdatastore.Jockey

class JockeyDTO {
    Long id
    String name
    Integer numberOfRaces
    Integer numberOfVictories
    List<String> lastResults

    JockeyDTO() {}

    JockeyDTO(Jockey jockey) {
        this.id = jockey.id
        this.name = jockey.name
        this.numberOfRaces = jockey.numberOfRaces
        this.numberOfVictories = jockey.numberOfVictories
        this.lastResults = jockey.lastResults
    }
}
