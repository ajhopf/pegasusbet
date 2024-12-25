package users

import java.time.LocalDateTime

class Bet {
    Long raceHorseJockeyId
    BigDecimal amount
    LocalDateTime timeStamp
    User user
    BetStatus status

    static belongsTo = [user: User]

    static constraints = {
        raceHorseJockeyId nullable: false
        amount nullable: false
        timeStamp nullable: false
        user nullable: false
        status nullable: false
    }

    static mapping = {
     table 'bets'
    }


    @Override
    String toString() {
        return "Bet{" +
                "raceHorseJockeyId=" + raceHorseJockeyId +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                ", user=" + user +
                ", status=" + status +
                '}';
    }
}
