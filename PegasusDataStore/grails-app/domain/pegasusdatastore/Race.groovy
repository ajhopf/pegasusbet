package pegasusdatastore

class Race {
    RaceCourse raceCourse
    String date
    String time

    static constraints = {
        raceCourse nullable: false
        date nullable: false
        time nullable: false
    }
}
