package pegasusdatastore


class Jockey {
    String name
    int numberOfRaces
    int numberOfVictories

    static constraints = {
        name nullable: false
    }

    static hasMany = [raceHorseJockey: RaceHorseJockey, jockeyResults: JockeyResults]

    @Override
    String toString() {
        return "Jockey{" +
                "name='" + name + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", results=" + jockeyResults +
                '}';
    }
}
