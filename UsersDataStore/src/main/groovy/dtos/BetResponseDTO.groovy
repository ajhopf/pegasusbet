package dtos

import enums.BetType
import users.Bet
import enums.BetStatus

import java.time.LocalDateTime

class BetResponseDTO {
    Long id
    BigDecimal amount
    BetType betType
    Long raceHorseJockeyId
    LocalDateTime timeStamp
    BetStatus status

    BetResponseDTO(Bet bet) {
        this.id = bet.id
        this.amount = bet.amount
        this.betType = bet.betType
        this.raceHorseJockeyId = bet.raceHorseJockeyId
        this.timeStamp = bet.timeStamp
        this.status = bet.status
    }
}
