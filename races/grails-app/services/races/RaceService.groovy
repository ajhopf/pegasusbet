package races

import grails.gorm.transactions.Transactional

@Transactional
class RaceService {

    Race addRace(RaceCourse raceCourse, String date, String time) {
        Race race = new Race(
                raceCourse: raceCourse,
                date: date,
                time: time
        )

        Race savedRace = race.save()

        return savedRace
    }
}
