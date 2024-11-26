package races

class RaceCourse {
    String name

    static constraints = {
        name unique: true, nullable: false
    }
}
