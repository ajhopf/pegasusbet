package pegasusdatastore

import java.time.LocalTime

class RaceHorseJockey {
    Race race
    Horse horse
    Jockey jockey
    String position
    LocalTime raceTime

    static constraints = {
        race nullable: false
        horse nullable: false
        jockey nullable: false
        position nullable: true
        raceTime nullable: true
    }

    static belongsTo = [race: Race, horse: Horse, jockey: Jockey]
}
