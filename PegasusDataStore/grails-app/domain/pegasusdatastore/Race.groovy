package pegasusdatastore

import java.sql.Date
import java.sql.Time

class Race {
    RaceCourse raceCourse
    Date date
    Time time

    static constraints = {
        raceCourse nullable: false
        date nullable: false
        time nullable: false
    }

    static hasOne = [raceCourse: RaceCourse]
    static hasMany = [raceHorseJockey: RaceHorseJockey]
}
