package dtos

import users.Bet
import users.BetStatus

import java.time.LocalDateTime

class BetResponseDTO {
    Long id
    BigDecimal amount
    Long raceHorseJockeyId
    LocalDateTime timeStamp
    BetStatus status

    BetResponseDTO(Bet bet) {
        this.id = bet.id
        this.amount = bet.amount
        this.raceHorseJockeyId = bet.raceHorseJockeyId
        this.timeStamp = bet.timeStamp
        this.status = bet.status
    }
}
