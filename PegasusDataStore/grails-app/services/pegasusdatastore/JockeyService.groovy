package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Service(Jockey)
abstract class JockeyService implements IJockeyService {

    @Transactional
    Jockey addJockey(String jockeyJson) {
        ObjectMapper objectMapper = new ObjectMapper()
        Jockey jockey = objectMapper.readValue(jockeyJson, Jockey.class)

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

    @Override
    @Transactional
    boolean deleteJockey(Serializable id){
        Jockey jockey = Jockey.get(id)

        if (jockey == null) {
            return false
        } else {
            jockey.delete()
            return true
        }
    }

}
