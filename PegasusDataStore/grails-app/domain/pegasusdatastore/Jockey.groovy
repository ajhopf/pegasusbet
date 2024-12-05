package pegasusdatastore


class Jockey {
    String name
    int numberOfRaces
    int numberOfVictories
    List<String> lastResults

    static constraints = {
        name nullable: false
    }

    static hasMany = [raceHorseJockey: RaceHorseJockey]

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
