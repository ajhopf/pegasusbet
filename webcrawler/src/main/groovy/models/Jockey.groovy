package models

class Jockey {
    String name
    int numberOfRaces
    int numberOfVictories
    List<String> lastResults

    static constraints = {
    }


    @Override
    public String toString() {
        return "Jockey{" +
                "name='" + name + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", lastResults=" + lastResults +
                '}';
    }
}
