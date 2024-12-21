package models

class Jockey {
    String name
    int numberOfRaces
    int numberOfVictories
    List<Results> results

    static constraints = {
    }


    @Override
    public String toString() {
        return "Jockey{" +
                "name='" + name + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", results=" + results +
                '}';
    }
}
