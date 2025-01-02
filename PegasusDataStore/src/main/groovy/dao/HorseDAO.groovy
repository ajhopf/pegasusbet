package dao

import pegasusdatastore.Horse
import pegasusdatastore.HorseResults

import javax.transaction.Transactional

@Transactional
class HorseDAO {
    Horse getHorseById(Long id) {
        return Horse.get(id)
    }

    Horse saveHorse(Horse horse) {
        return horse.save(flush: true)
    }

    void saveHorseResults(HorseResults horseResults) {
        horseResults.save(flush: true)
    }
}
