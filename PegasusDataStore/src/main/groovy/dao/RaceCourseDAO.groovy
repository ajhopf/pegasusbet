package dao

import pegasusdatastore.RaceCourse

class RaceCourseDAO {
    RaceCourse getRaceCourseById(Long id) {
        return RaceCourse.get(id)
    }
}
