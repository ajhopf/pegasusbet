package models

class Horse {
    String name
    String sex
    String age
    int numberOfRaces
    int numberOfVictories
    List<Results> results

    static constraints = {
    }


    @Override
    public String toString() {
        return "Horse{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", numberOfRaces=" + numberOfRaces +
                ", numberOfVictories=" + numberOfVictories +
                ", results=" + results +
                '}';
    }
}
