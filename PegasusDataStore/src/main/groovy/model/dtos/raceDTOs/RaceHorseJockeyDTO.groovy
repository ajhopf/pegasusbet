package model.dtos.raceDTOs

import pegasusdatastore.RaceHorseJockey

class RaceHorseJockeyDTO {
    Long horseId
    Long jockeyId
    String position
    String raceTime

    RaceHorseJockeyDTO(RaceHorseJockey raceHorseJockey) {
        this.horseId = raceHorseJockey.horseId
        this.jockeyId = raceHorseJockey.jockeyId
        this.position = raceHorseJockey.position
        this.raceTime = raceHorseJockey.raceTime
    }
}
