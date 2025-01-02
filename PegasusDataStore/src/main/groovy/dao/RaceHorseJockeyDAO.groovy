package dao

import pegasusdatastore.RaceHorseJockey

class RaceHorseJockeyDAO {
    RaceHorseJockey getRaceHorseJockeyById(Long id) {
        return RaceHorseJockey.get(id)
    }

    RaceHorseJockey saveRaceHorseJockey(RaceHorseJockey raceHorseJockey) {
        return raceHorseJockey.save(flush: true)
    }
}
