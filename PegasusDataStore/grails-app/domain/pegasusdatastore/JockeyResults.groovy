package pegasusdatastore

import java.time.LocalDate

class JockeyResults {
    Jockey jockey
    String result
    LocalDate date

    static constraints = {
        jockey nullable: false
        result nullable: false, maxSize: 10
        date nullable: false
    }

    static belongsTo = [jockey: Jockey]

    @Override
    String toString() {
        return "JockeyResults{" +
                "jockey=" + jockey.name +
                ", result='" + result + '\'' +
                ", date=" + date +
                '}';
    }
}
