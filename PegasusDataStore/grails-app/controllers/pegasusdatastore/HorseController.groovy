package pegasusdatastore

import grails.converters.JSON
import grails.validation.ValidationException
import model.dtos.HorseDTO
import model.mappers.HorseMapper

class HorseController {
    HorseService horseService

    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, Integer offset) {
        max = Math.min(max ?: 10, 100)
        offset = offset ?: 0

        List<HorseDTO> horseDTOList = horseService.list([max: max, offset: offset])

        render(status: 200, contentType: "application/json") {
            "horses" horseDTOList as JSON
            "items" max
            "offsetItems" offset
        }
    }

    def show(Long id) {
        def horseDTO = horseService.getHorse(id)

        if (!horseDTO) {
            notFound()
            return
        }

        render(status: 200, contentType: "application/json") {
            "horse"  horseDTO as JSON
        }
    }

    def save(Horse newHorse) {
        try {
            newHorse = horseService.save(newHorse)

        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                message "Horse not saved"
                validationErrors newHorse.errors.fieldErrors.collect { fieldError ->
                    [
                            field: fieldError.field,
                            rejectedValue: fieldError.rejectedValue,
                    ]
                }
            }
            return
        }

        render (status: 201, contentType: "application/json") {
            "horse" HorseMapper.toDTO(newHorse) as JSON
        }
    }


    def update(Horse updatedHorse) {
        if (updatedHorse == null) {
            notFound()
            return
        }

        try {
            updatedHorse = horseService.save(updatedHorse)
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                validationError "Horse not updated"
                updatedHorse.errors.fieldErrors.each {
                    field it.field
                }
            }
            return
        }

        render(contentType: "application/json", status: 200) {
            "horse" HorseMapper.toDTO(updatedHorse) as JSON
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        boolean deleted = horseService.deleteHorse(id)

        if (deleted) {
            render(status: 204, contentType: "application/json") {}
        } else {
            render(status: 404, contentType: "application/json") {
                message "Horse not found, could not delete"
            }
        }

    }

    protected void notFound() {
        render (contentType: "application/json", status: 404) {
            message "Resource not found"
        }
    }
}
