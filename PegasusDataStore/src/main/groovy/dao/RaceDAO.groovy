package dao

import pegasusdatastore.Race

class RaceDAO {
    Race getRaceById(Long id) {
        return Race.get(id)
    }

    List<Race> listRaces(Map params) {
        return Race.list(offset: params.offset, max: params.max)
    }

    Race saveRace(Race race) {
        return race.save(flush: true)
    }
}
