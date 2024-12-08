package pegasusdatastore

import grails.validation.ValidationException
import model.dtos.RaceCourseDTO
import model.mappers.RaceCourseMapper

class RaceCourseController {

    RaceCourseService raceCourseService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, Integer offset) {
        max = Math.min(max ?: 10, 100)
        offset = offset ?: 0

        List<RaceCourseDTO> raceCourseDTOList = raceCourseService.list([max: max, offset: offset])

        render(status: 200, contentType: "application/json") {
            "raceCourses" raceCourseDTOList
            "items" max
            "offsetItems" offset
        }
    }

    def show(Long id) {
        def raceCourseDTO = raceCourseService.getRaceCourse(id)

        if (!raceCourseDTO) {
            notFound()
            return
        }

        render(status: 200, contentType: "application/json") {
            "raceCourse" raceCourseDTO
        }
    }

    def save(RaceCourse newRaceCourse) {
        try {
            newRaceCourse = raceCourseService.save(newRaceCourse)
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                validationError "RaceCourse not saved"
                newRaceCourse.errors.fieldErrors.each {
                    field it.field
                }
            }
            return
        }

        render (status: 201, contentType: "application/json") {
            "raceCourse" RaceCourseMapper.toDTO(newRaceCourse)
        }
    }


    def update(RaceCourse updatedRaceCourse) {
        if (updatedRaceCourse == null) {
            notFound()
            return
        }

        try {
            updatedRaceCourse = raceCourseService.save(updatedRaceCourse)
        } catch (ValidationException e) {
            render(status: 400, contentType: "application/json") {
                validationError "RaceCourse not updated"
                updatedRaceCourse.errors.fieldErrors.each {
                    field it.field
                }
            }
            return
        }


        render(contentType: "application/json", status: 200) {
            "raceCourse" RaceCourseMapper.toDTO(updatedRaceCourse)
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        boolean deleted = raceCourseService.deleteRaceCourse(id)

        if (deleted) {
            render(status: 204, contentType: "application/json") {}
        } else {
            render(status: 404, contentType: "application/json") {
                message "RaceCourse not found, could not delete"
            }
        }

    }

    protected void notFound() {
        render (contentType: "application/json", status: 404) {
            message "Resource not found"
        }
    }
}
