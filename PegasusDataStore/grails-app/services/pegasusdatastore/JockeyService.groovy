package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import grails.gorm.transactions.Transactional

@Transactional
class JockeyService {
    ObjectMapper objectMapper

    JockeyService() {
        this.objectMapper = new ObjectMapper()
    }

    Jockey addJockey(String jockeyJson) {
        Jockey jockey = this.objectMapper.readValue(jockeyJson, Jockey.class)

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
