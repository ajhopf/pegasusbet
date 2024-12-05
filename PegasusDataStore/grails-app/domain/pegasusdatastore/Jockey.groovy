package pegasusdatastore

import com.fasterxml.jackson.annotation.JsonIgnore

class Jockey {
    String name
    int numberOfRaces
    int numberOfVictories
    List<String> lastResults

    static constraints = {
        name nullable: false
    }


    @Override
    String toString() {
        return "Jockey{" +
                "name='" + name + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", lastResults=" + lastResults +
                '}';
    }
}
