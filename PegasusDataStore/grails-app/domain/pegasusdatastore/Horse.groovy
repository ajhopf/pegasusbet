package pegasusdatastore

class Horse {
    String name
    String sex
    String age
    int numberOfRaces
    int numberOfVictories
    List<String> lastResults

    static constraints = {
    }


    @Override
    String toString() {
        return "Horse{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", lastResults=" + lastResults +
                '}';
    }
}
