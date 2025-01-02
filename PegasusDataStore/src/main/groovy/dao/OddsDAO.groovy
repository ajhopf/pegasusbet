package dao

import pegasusdatastore.Odds
import pegasusdatastore.RaceHorseJockey

class OddsDAO {
    List<Odds> listOdds(Map params) {
        return Odds.list(offset: params.offset, max: params.max)
    }

    Odds findByRaceHorseJockey(RaceHorseJockey raceHorseJockey) {
        return Odds.findByRaceHorseJockey(raceHorseJockey)
    }

    Odds saveOdds(Odds odds) {
        return odds.save(flush: true)
    }
}
