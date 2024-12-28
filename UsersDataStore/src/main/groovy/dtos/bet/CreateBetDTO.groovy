package dtos.bet

import enums.BetType
import grails.validation.Validateable

class CreateBetDTO implements Validateable {
    BigDecimal amount
    BetType betType
    Long raceHorseJockeyId
}
