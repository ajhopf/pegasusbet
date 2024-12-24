package dtos

import grails.validation.Validateable

class CreateBetDTO implements Validateable {
    BigDecimal amount
    Long raceHorseJockeyId
}
