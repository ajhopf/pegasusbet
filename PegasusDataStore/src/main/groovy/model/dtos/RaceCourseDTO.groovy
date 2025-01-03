package model.dtos

import pegasusdatastore.RaceCourse

class RaceCourseDTO {
    Long id
    String name

    RaceCourseDTO() {}

    RaceCourseDTO(RaceCourse raceCourse) {
        this.id = raceCourse.id
        this.name = raceCourse.name
    }
}
