package pegasusdatastore

import exceptions.ResourceNotFoundException
import grails.converters.JSON
import model.dtos.raceDTOs.RaceRequestDTO
import model.dtos.raceDTOs.RaceResponseDTO
import model.mappers.RaceMapper
import org.springframework.validation.FieldError

class RaceController {
    RaceService raceService

    def index(Integer max, Integer offset) {
        max = Math.min(max ?: 10, 100)
        offset = offset ?: 0

        List<RaceResponseDTO> racesList = raceService.list([max: max, offset: offset])

        render(status: 200, contentType: "application/json") {
            "races" racesList as JSON
            "items" max
            "offsetItems" offset
        }

    }

    def save(RaceRequestDTO raceRequestDTO) {
        if (!raceRequestDTO.validate()) {
            renderError("Race not saved", raceRequestDTO)
            return
        }

        try {
            Race newRace = raceService.save(raceRequestDTO)

            render (status: 201, contentType: "application/json") {
                race RaceMapper.toResponseDTO(newRace) as JSON
            }
        } catch (ResourceNotFoundException e) {
            render (status: 404, contentType: "application/json") {
                message e.message ?: "Resource not found"
            }
        }


    }

    void renderError(String errorMessage, RaceRequestDTO requestDTO) {
        render (status: 400, contentType: "application/json") {
            message errorMessage
            validationErrors requestDTO.errors.fieldErrors.collect { fieldError ->
                [
                        field: fieldError.field,
                        rejectedValue: fieldError.rejectedValue,
                        message: g.message(error: fieldError)
                ]
            }
        }
    }
}
