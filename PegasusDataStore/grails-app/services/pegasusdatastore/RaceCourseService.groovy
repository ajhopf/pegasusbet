package pegasusdatastore

import grails.gorm.transactions.Transactional

@Transactional
class RaceCourseService {

    synchronized RaceCourse addRaceCourse(String name) {
        RaceCourse existingRaceCourse = RaceCourse.findByName(name, [lock: true])

        if (!existingRaceCourse) {
            RaceCourse newRaceCourse = new RaceCourse(name: name)
            newRaceCourse.save(flush: true)
            return newRaceCourse
        }

        return existingRaceCourse
    }
}
