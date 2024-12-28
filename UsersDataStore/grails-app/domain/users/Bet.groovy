package users

import enums.BetStatus
import enums.BetType

import java.time.LocalDateTime

class Bet {
    Long raceHorseJockeyId
    BigDecimal amount
    LocalDateTime timeStamp
    BetType betType
    User user
    BetStatus status
    boolean resultViewed

    static belongsTo = [user: User]

    static constraints = {
        raceHorseJockeyId nullable: false
        amount nullable: false
        timeStamp nullable: false
        betType nullable: false
        user nullable: false
        status nullable: false
        resultViewed nullable: false
    }

    static mapping = {
        table 'bets'
        version false
    }


    @Override
    String toString() {
        return "Bet{" +
                "raceHorseJockeyId=" + raceHorseJockeyId +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                ", status=" + status +
                '}'
    }
}
