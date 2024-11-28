package races

import grails.gorm.transactions.Transactional

@Transactional
class JockeyService {

    Jockey addJockey(Jockey jockey) {
        Jockey existingJockey = Jockey.findByName(jockey.name, [lock: true])

        if (!existingJockey) {
            Jockey newJockey = jockey.save(flush: true)

            return newJockey
        } else {
            existingJockey.numberOfRaces = jockey.numberOfRaces
            existingJockey.lastResults = jockey.lastResults
            existingJockey.numberOfVictories = jockey.numberOfVictories

            existingJockey.save()

            return existingJockey
        }
    }

}
