package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import grails.gorm.transactions.Transactional

@Transactional
class HorseService {
    ObjectMapper objectMapper

    HorseService() {
        this.objectMapper = new ObjectMapper()
    }

    Horse addHorse(Horse horse) {
        horse.save()
    }

    Horse addHorse(String horsejson) {
        Horse horse = this.objectMapper.readValue(horsejson, Horse.class)

        Horse existingHorse = Horse.findByNameAndAge(horse.name, horse.age, [lock: true])

        println existingHorse

        if (existingHorse == null) {
            Horse newHorse = horse.save(flush: true)
            return newHorse
        } else {
            existingHorse.numberOfRaces = horse.numberOfRaces
            existingHorse.lastResults = horse.lastResults
            existingHorse.numberOfVictories = horse.numberOfVictories
            existingHorse.save(flush: true)

            return existingHorse
        }
    }
}
