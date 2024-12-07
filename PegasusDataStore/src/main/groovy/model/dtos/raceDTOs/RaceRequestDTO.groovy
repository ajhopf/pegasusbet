package model.dtos.raceDTOs

import grails.validation.Validateable

class RaceRequestDTO implements Validateable  {
    Long raceCourseId
    String time
    String date
    List<HorseJockeyDTO> horseJockeys

    static constraints = {
        raceCourseId nullable: false
        time nullable: false, matches: /\d{2}:\d{2}/
        date nullable: false, matches: /\d{2}\/\d{2}\/\d{4}/
        horseJockeys minSize: 1
    }
}
