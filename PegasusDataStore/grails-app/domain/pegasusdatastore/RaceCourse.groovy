package pegasusdatastore

class RaceCourse {
    String name

    static constraints = {
        name unique: true, nullable: false,  maxSize: 100
    }

    static hasMany = [races: Race]

    @Override
    String toString() {
        return "RaceCourse{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
