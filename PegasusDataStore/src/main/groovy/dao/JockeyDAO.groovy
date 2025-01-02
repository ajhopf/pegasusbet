package dao

import pegasusdatastore.Jockey
import pegasusdatastore.JockeyResults

class JockeyDAO {
    Jockey getJockeyById(Long id) {
        return Jockey.get(id)
    }

    Jockey saveJockey(Jockey jockey) {
        return jockey.save(flush: true)
    }

    void saveJockeyResults(JockeyResults jockeyResults) {
        jockeyResults.save(flush: true)
    }
}
