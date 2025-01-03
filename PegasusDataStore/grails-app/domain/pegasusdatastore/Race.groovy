package pegasusdatastore

import java.time.LocalDate
import java.time.LocalTime

class Race {
    RaceCourse raceCourse
    LocalDate date
    LocalTime time
    boolean finished

    static constraints = {
        raceCourse nullable: false
        date nullable: false
        time nullable: false
        finished nullable: false
    }

    static hasOne = [raceCourse: RaceCourse]
    static hasMany = [raceHorseJockey: RaceHorseJockey]

    @Override
    String toString() {
        return "Race{" +
                "id=" + id +
                ", raceCourse=" + raceCourse +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
