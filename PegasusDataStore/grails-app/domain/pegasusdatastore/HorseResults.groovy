package pegasusdatastore

import java.time.LocalDate

class HorseResults {
    Horse horse
    String result
    LocalDate date

    static constraints = {
        horse nullable: false
        result nullable: false, maxSize: 10
        date nullable: false
    }

    static belongsTo = [horse: Horse]

    @Override
    String toString() {
        return "HorseResults{" +
                "horse=" + horse.name +
                ", result='" + result + '\'' +
                ", date=" + date +
                '}';
    }
}
