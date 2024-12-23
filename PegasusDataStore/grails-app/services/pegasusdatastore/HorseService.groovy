package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import exceptions.ResourceAlreadyExistsException
import exceptions.ResourceNotFoundException
import grails.gorm.transactions.Transactional
import model.dtos.horseDTOs.HorseResponseDTO
import model.dtos.horseDTOs.HorseRequestDTO
import model.mappers.HorseMapper

import java.time.LocalDate

@Transactional
class HorseService  {
    void saveHorseFromKafka(String horseString) {
        ObjectMapper objectMapper = new ObjectMapper()

        Map<String, Object> horseData = objectMapper.readValue(horseString, Map.class)

        Horse horse = new Horse(
                name: horseData.name,
                sex: horseData.sex,
                age: horseData.age,
                numberOfRaces: horseData.numberOfRaces as int,
                numberOfVictories: horseData.numberOfVictories as int
        )

        Horse existingHorse = Horse.findByNameAndAge(horse.name, horse.age, [lock: true])

        if (!existingHorse) {
            horse = horse.save(flush: true)

            horseData.results.each { Map result ->
                String dateString = result.date

                int day = dateString.split('/')[0].toInteger()
                int month = dateString.split('/')[1].toInteger()
                int year = dateString.split('/')[2].toInteger() + 2000

                LocalDate date = LocalDate.of(year, month, day)

                HorseResults horseResults = new HorseResults(
                        horse: horse,
                        result: result.result,
                        date: date
                )

                horseResults.save(flush: true)
            }

        } else {
            println "Cavalo já existe."
        }


    }

    HorseResponseDTO save(HorseRequestDTO horseRequestDTO) {
        Horse horse = HorseMapper.fromDTO(horseRequestDTO)

        Horse existingHorse = Horse.findByNameAndAge(horse.name, horse.age, [lock: true])

        if (!existingHorse) {
            Horse newHorse = horse.save(flush: true)
            return HorseMapper.toResponseDTO(newHorse)
        } else {
            throw new ResourceAlreadyExistsException("Cavalo já existe")
        }
    }

    HorseResponseDTO updateHorse(HorseRequestDTO horseRequestDTO, Long id) {
        Horse existingHorse = Horse.get(id)

        if (!existingHorse) {
            throw new ResourceNotFoundException("Cavalo não encontrado com id $id")
        }

        if (horseRequestDTO.name) {
            existingHorse.name = horseRequestDTO.name
        }

        if (horseRequestDTO.age) {
            existingHorse.age = horseRequestDTO.age
        }

        if (horseRequestDTO.numberOfRaces) {
            existingHorse.numberOfRaces = horseRequestDTO.numberOfRaces
        }

        if (horseRequestDTO.numberOfVictories) {
            existingHorse.numberOfVictories = horseRequestDTO.numberOfVictories
        }


        if (horseRequestDTO.horseResults && horseRequestDTO.horseResults.size() > 0) {
            // Remove associações e deleta os resultados
            HorseResults.deleteAll(existingHorse.horseResults)
            existingHorse.horseResults.clear()

            // Adiciona novos resultados
            horseRequestDTO.horseResults.each { newResult ->
                newResult.horse = existingHorse
                newResult.save(flush: true, failOnError: true)
            }
        }

        existingHorse.save(flush: true)

        existingHorse.horseResults = horseRequestDTO.horseResults

        return HorseMapper.toResponseDTO(existingHorse)
    }

    List<HorseResponseDTO> list(Map params) {
        List<Horse> results = Horse.list(params)

        return HorseMapper.toDTOs(results)
    }

    HorseResponseDTO getHorse(Long id) {
        Horse horse = Horse.get(id)

        if (!horse) {
            throw new ResourceNotFoundException("Cavalo com id $id não encontrado")
        }

        return HorseMapper.toResponseDTO(horse)
    }

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
