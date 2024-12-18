package pegasusdatastore

import java.time.LocalDateTime

class Odds {
    RaceHorseJockey raceHorseJockey
    Double rating
    Double initialOdd
    Double currentOdd
    LocalDateTime lastUpdated = LocalDateTime.now()

    static constraints = {
        rating nullable: false
        initialOdd nullable: true
        currentOdd nullable: true
    }

    static mapping = {
        lastUpdated column: 'last_updated'
    }

    static belongsTo = [raceHorseJockey: RaceHorseJockey]


    @Override
    public String toString() {
        return "Odds{" +
                "raceHorseJockey=" + raceHorseJockey +
                ", rating=" + rating +
                ", initialOdd=" + initialOdd +
                ", currentOdd=" + currentOdd +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
