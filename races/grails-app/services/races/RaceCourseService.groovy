package races

import grails.gorm.transactions.Transactional

@Transactional
class RaceCourseService {

    RaceCourse addRaceCourse(String name) {
        RaceCourse existingRaceCourse = RaceCourse.findByName(name)

        if (!existingRaceCourse) {
            RaceCourse newRaceCourse = new RaceCourse(name: name)
            newRaceCourse = newRaceCourse.save()

            return newRaceCourse
        }

        return existingRaceCourse
    }
}
