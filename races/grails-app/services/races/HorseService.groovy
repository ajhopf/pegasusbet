package races

import grails.gorm.transactions.Transactional

@Transactional
class HorseService {

    Horse addHorse(Horse horse) {
        Horse existingHorse = Horse.findByNameAndAge(horse.name, horse.age)

        if (!existingHorse) {
            Horse newHorse = horse.save()
            return newHorse
        } else {
            existingHorse.numberOfRaces = horse.numberOfRaces
            existingHorse.lastResults = horse.lastResults
            existingHorse.numberOfVictories = horse.numberOfVictories
            existingHorse.save()

            return existingHorse
        }


    }
}
