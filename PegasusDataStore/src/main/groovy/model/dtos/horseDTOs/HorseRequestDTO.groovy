package model.dtos.horseDTOs

import grails.validation.Validateable
import pegasusdatastore.HorseResults

class HorseRequestDTO implements Validateable {
    String name
    String age
    String sex
    Integer numberOfRaces
    Integer numberOfVictories
    List<HorseResults> horseResults

    static constraints = {
        name nullable: false
        age nullable: false
        sex nullable: false
        numberOfRaces nullable: false
        numberOfVictories nullable: false
        horseResults nullable: false
    }
}
