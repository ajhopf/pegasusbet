package pegasusdatastore

import exceptions.ResourceAlreadyExistsException
import exceptions.ResourceNotFoundException
import grails.gorm.transactions.Transactional
import model.dtos.horseDTOs.HorseResponseDTO
import model.dtos.horseDTOs.HorseRequestDTO
import model.mappers.HorseMapper

@Transactional
class HorseService  {
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
