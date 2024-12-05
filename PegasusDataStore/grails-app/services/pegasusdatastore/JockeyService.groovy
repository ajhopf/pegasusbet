package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import model.dtos.JockeyDTO
import model.mappers.JockeyMapper
import pegasusdatastore.interfaces.IJockeyService

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
    List<JockeyDTO> list(Map params) {
        List<Jockey> jockeys = Jockey.list(offset: params.offset, max: params.max)
        return JockeyMapper.toDTOs(jockeys)
    }

    @Override
    @Transactional
    JockeyDTO getJockey(Long id) {
        Jockey jockey = Jockey.get(id)
        return JockeyMapper.toDTO(jockey)
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
