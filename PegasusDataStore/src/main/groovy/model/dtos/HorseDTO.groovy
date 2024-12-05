package model.dtos

import pegasusdatastore.Horse

class HorseDTO {
    Long id
    String name
    String age
    String sex
    Integer numberOfRaces
    Integer numberOfVictories
    List<String> lastResults

    HorseDTO() {}

    HorseDTO(Horse horse) {
        this.id = horse.id
        this.name = horse.name
        this.age = horse.age
        this.sex = horse.sex
        this.numberOfRaces = horse.numberOfRaces
        this.numberOfVictories = horse.numberOfVictories
        this.lastResults = horse.lastResults
    }

}
