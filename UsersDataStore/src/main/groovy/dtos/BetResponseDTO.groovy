package dtos

import users.Bet

import java.time.LocalDateTime

class BetResponseDTO {
    Long id
    BigDecimal amount
    Long raceHorseJockeyId
    LocalDateTime timeStamp

    BetResponseDTO(Bet bet) {
        this.id = bet.id
        this.amount = bet.amount
        this.raceHorseJockeyId = bet.raceHorseJockeyId
        this.timeStamp = bet.timeStamp
    }
}
