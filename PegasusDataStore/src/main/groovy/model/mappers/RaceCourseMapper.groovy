package model.mappers

import model.dtos.RaceCourseDTO
import pegasusdatastore.RaceCourse

class RaceCourseMapper {
    static RaceCourseDTO toDTO(RaceCourse raceCourse) {
        return new RaceCourseDTO(
                id: raceCourse.id,
                name: raceCourse.name,
        )
    }

    static List<RaceCourseDTO> toDTOs(List<RaceCourse> raceCourses) {
        return raceCourses.collect { toDTO(it) }
    }
}
