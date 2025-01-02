package dao

import users.Bet
import users.User

class BetDAO {
    List<Bet> findAllBetsByUser(User user) {
        return Bet.findAllByUser(user)
    }

    List<Bet> findAllByRaceHorseJockeyId(Long raceHorseJockeyId){
        Bet.findAllByRaceHorseJockeyId(raceHorseJockeyId)
    }

    Bet saveBet(Bet bet) {
        bet.save(flush: true)
    }
}
