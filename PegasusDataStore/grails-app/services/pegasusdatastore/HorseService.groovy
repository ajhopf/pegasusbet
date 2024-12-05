package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import model.dtos.HorseDTO
import model.mappers.HorseMapper
import pegasusdatastore.interfaces.IHorseService

@Service(Horse)
abstract class HorseService implements IHorseService {

    @Transactional
    Horse addHorse(String horsejson) {
        ObjectMapper objectMapper = new ObjectMapper()
        Horse horse = objectMapper.readValue(horsejson, Horse.class)

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

    @Override
    @Transactional
    List<HorseDTO> list(Map params) {
        List<Horse> horses = Horse.list(offset: params.offset, max: params.max)
        return HorseMapper.toDTOs(horses)
    }

    @Override
    @Transactional
    HorseDTO getHorse(Long id) {
        Horse horse = Horse.get(id)
        return HorseMapper.toDTO(horse)
    }

    @Override
    @Transactional
    boolean deleteHorse(Serializable id){
        Horse horse = Horse.get(id)

        if (horse == null) {
            return false
        } else {
            horse.delete()
            return true
        }
    }
}
