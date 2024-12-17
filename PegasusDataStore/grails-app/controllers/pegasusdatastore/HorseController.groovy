package pegasusdatastore

import exceptions.ResourceAlreadyExistsException
import exceptions.ResourceNotFoundException
import grails.validation.ValidationException
import model.dtos.horseDTOs.HorseResponseDTO
import model.dtos.horseDTOs.HorseRequestDTO

class HorseController {
    HorseService horseService

    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, Integer offset, String filter, String filterField) {
        max = max ?: 1000
        offset = offset ?: 0

        if (!filterField) {
            filterField = 'name'
        }

        if (!filter) {
            filter = ''
        }

        List<HorseResponseDTO> horseDTOList = horseService.list([max: max, offset: offset, filterField: filterField, filter: filter])

        render(status: 200, contentType: "application/json") {
            "horses" horseDTOList
            "items" horseDTOList.size()
            "offsetItems" offset
        }
    }

    def show(Long id) {
        try {
            HorseResponseDTO horseDTO = horseService.getHorse(id)

            render(status: 200, contentType: "application/json") {
                "horse"  horseDTO
            }
        } catch (ResourceNotFoundException e) {
            render(status: 404, contentType: "application/json") {
                error e.getMessage()
            }
        }
    }

    def save(HorseRequestDTO newHorse) {
        if (!newHorse.validate()) {
            renderError("Horse not saved", newHorse)
            return
        }

        try {
            HorseResponseDTO savedHorse = horseService.save(newHorse)

            render (status: 201, contentType: "application/json") {
                "horse" savedHorse
            }
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                validationError "Horse not saved"
                newHorse.errors.fieldErrors.each {
                    field it.field
                }
            }
        } catch (ResourceAlreadyExistsException e) {
            render(status: 409, contentType: "application/json") {
                message e.getMessage()
            }
        }

    }


    def update(HorseRequestDTO updatedHorse, Long id) {
        if (updatedHorse == null) {
            notFound()
            return
        }

        try {
            HorseResponseDTO responseDTO = horseService.updateHorse(updatedHorse, id)

            render(contentType: "application/json", status: 200) {
                "horse" responseDTO
            }
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                validationError "Horse not updated"
                updatedHorse.errors.fieldErrors.each {
                    field it.field
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

    void renderError(String errorMessage, HorseRequestDTO requestDTO) {
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
