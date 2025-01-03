package pegasusdatastore

import exceptions.ResourceAlreadyExistsException
import exceptions.ResourceNotFoundException
import grails.validation.ValidationException
import model.dtos.jockeyDTOs.JockeyRequestDTO
import model.dtos.jockeyDTOs.JockeyResponseDTO

class JockeyController {

    JockeyService jockeyService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, Integer offset, String filter, String filterField) {
        max = max ?: 1000
        offset = offset ?: 0

        if (!filterField) {
            filterField = 'name'
        }

        if (!filter) {
            filter = ''
        }

        List<JockeyResponseDTO> jockeyDTOList = jockeyService.list([max: max, offset: offset, filterField: filterField, filter: filter])

        render(status: 200, contentType: "application/json") {
            "jockeys" jockeyDTOList
            "items" max
            "offsetItems" offset
        }
    }

    def show(Long id) {
        try {
            JockeyResponseDTO jockeyDTO = jockeyService.getJockey(id)

            render(status: 200, contentType: "application/json") {
                "jockey" jockeyDTO
            }
        } catch (ResourceNotFoundException e) {
            render(status: 404, contentType: "application/json") {
                error e.getMessage()
            }
        }
    }

    def save(JockeyRequestDTO newJockey) {
        if (!newJockey.validate()) {
            renderError("Horse not saved", newHorse)
            return
        }

        try {
            JockeyResponseDTO savedJockey = jockeyService.save(newJockey)

            render (status: 201, contentType: "application/json") {
                "jockey" savedJockey
            }
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                message "Jockey not saved"
                validationErrors newJockey.errors.fieldErrors.collect { fieldError ->
                    [
                            field: fieldError.field,
                            rejectedValue: fieldError.rejectedValue,
                    ]
                }
            }
            return
        } catch (ResourceAlreadyExistsException e) {
            render(status: 409, contentType: "application/json") {
                message e.getMessage()
            }
        }


    }


    def update(JockeyRequestDTO updatedJockey, Long id) {
        if (updatedJockey == null) {
            notFound()
            return
        }

        try {
            JockeyResponseDTO responseDTO = jockeyService.updateJockey(updatedJockey, id)

            render(contentType: "application/json", status: 200) {
                "jockey" responseDTO
            }
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                message "Jockey not updated"
                validationErrors updatedJockey.errors.fieldErrors.collect { fieldError ->
                    [
                            field: fieldError.field,
                            rejectedValue: fieldError.rejectedValue,
                    ]
                }
            }
        } catch (ResourceNotFoundException e) {
            render(status: 404, contentType: "application/json") {
                validationError e.getMessage()
            }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        boolean deleted = jockeyService.deleteJockey(id)

        if (deleted) {
            render(status: 204, contentType: "application/json") {}
        } else {
            render(status: 404, contentType: "application/json") {
                message "Jockey not found, could not delete"
            }
        }

    }

    protected void notFound() {
        render (contentType: "application/json", status: 404) {
            message: "Resource not found"
        }
    }

    void renderError(String errorMessage, JockeyRequestDTO requestDTO) {
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
