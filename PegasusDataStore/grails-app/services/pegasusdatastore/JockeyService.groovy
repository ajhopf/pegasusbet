package pegasusdatastore

import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.ResourceAlreadyExistsException
import exceptions.ResourceNotFoundException

import grails.gorm.transactions.Transactional
import model.dtos.jockeyDTOs.JockeyRequestDTO
import model.dtos.jockeyDTOs.JockeyResponseDTO
import model.mappers.JockeyMapper

import java.time.LocalDate

@Transactional
class JockeyService {

    void saveJockeyFromKafka(String jockeyString) {
        ObjectMapper objectMapper = new ObjectMapper()

        Map<String, Object> jockeydata = objectMapper.readValue(jockeyString, Map.class)

        Jockey jockey = new Jockey(
                name: jockeydata.name,
                numberOfRaces: jockeydata.numberOfRaces as int,
                numberOfVictories: jockeydata.numberOfVictories as int
        )

        Jockey existingJockey = Jockey.findByName(jockey.name, [lock: true])

        if (!existingJockey) {
            jockey = jockey.save(flush: true)

            jockeydata.results.each { Map result ->
                println result

                String dateString = result.date

                int day = dateString.split('/')[0].toInteger()
                int month = dateString.split('/')[1].toInteger()
                int year = dateString.split('/')[2].toInteger()

                LocalDate date = LocalDate.of(day, month, year)

                JockeyResults jockeyResults = new JockeyResults(
                        jockey: jockey,
                        result: result.result,
                        date: date
                )

                jockeyResults.save(flush: true)
            }

        } else {
            println "Jockey já existe."
        }
    }


    JockeyResponseDTO save(JockeyRequestDTO jockeyRequestDTO) {
        Jockey jockey = JockeyMapper.fromDTO(jockeyRequestDTO)

        Jockey existingJockey = Jockey.findByName(jockey.name, [lock: true])

        if (!existingJockey) {
            Jockey newJockey = jockey.save(flush: true)

            return JockeyMapper.toResponseDTO(newJockey)
        } else {
            throw new ResourceAlreadyExistsException("Jockey já existe")
        }
    }

    JockeyResponseDTO updateJockey(JockeyRequestDTO jockeyRequestDTO, Long id) {
        Jockey existingJockey = Jockey.get(id)

        if (!existingJockey) {
            throw new ResourceNotFoundException("Jockey não encontrado com id $id")
        }

        if (jockeyRequestDTO.name) {
            existingJockey.name = jockeyRequestDTO.name
        }

        if (jockeyRequestDTO.numberOfRaces) {
            existingJockey.numberOfRaces = jockeyRequestDTO.numberOfRaces
        }

        if (jockeyRequestDTO.numberOfVictories) {
            existingJockey.numberOfVictories = jockeyRequestDTO.numberOfVictories
        }


        if (jockeyRequestDTO.jockeyResults?.size() > 0) {
            // Remove associações e deleta os resultados
            JockeyResults.deleteAll(existingJockey.jockeyResults)
            existingJockey.jockeyResults.clear()

            // Adiciona novos resultados
            jockeyRequestDTO.jockeyResults.each { newResult ->
                newResult.jockey = existingJockey
                newResult.save(flush: true, failOnError: true)
            }
        }

        existingJockey.save(flush: true)

        existingJockey.jockeyResults = jockeyRequestDTO.jockeyResults

        return JockeyMapper.toResponseDTO(existingJockey)
    }


    List<JockeyResponseDTO> list(Map params) {
        List<Jockey> results = Jockey.list(params)

        return JockeyMapper.toDTOs(results)
    }

    JockeyResponseDTO getJockey(Long id) {
        Jockey jockey = Jockey.get(id)

        if (!jockey) {
            throw new ResourceNotFoundException("Jockey com id $id não encontrado")
        }

        return JockeyMapper.toResponseDTO(jockey)
    }

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
