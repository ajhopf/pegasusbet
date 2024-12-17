package model.dtos.jockeyDTOs

import grails.validation.Validateable

import pegasusdatastore.JockeyResults

class JockeyRequestDTO implements Validateable  {
    String name
    Integer numberOfRaces
    Integer numberOfVictories
    List<JockeyResults> jockeyResults

    static constraints = {
        name nullable: false
        numberOfRaces nullable: false
        numberOfVictories nullable: false
        jockeyResults nullable: false
    }
}
