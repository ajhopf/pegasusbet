package pegasusdatastore

import java.sql.Time

class RaceHorseJockey {
    Race race
    Horse horse
    Jockey jockey
    String position
    Time raceTime

    static constraints = {
        race nullable: false
        horse nullable: false
        jockey nullable: false
        position nullable: true
    }

    static belongsTo = [race: Race, horse: Horse, jockey: Jockey]
}
