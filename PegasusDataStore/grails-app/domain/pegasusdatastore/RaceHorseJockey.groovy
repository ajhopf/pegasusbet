package pegasusdatastore

import java.time.LocalTime

class RaceHorseJockey {
    Race race
    Horse horse
    Integer number
    Jockey jockey
    String position
    LocalTime raceTime
    BigDecimal totalBetsAmount

    static constraints = {
        race nullable: false
        horse nullable: false
        number nullable: false
        jockey nullable: false
        position nullable: true
        raceTime nullable: true
        odds nullable: true
    }

    static hasOne = [odds: Odds]
    static belongsTo = [race: Race, horse: Horse, jockey: Jockey]
}
