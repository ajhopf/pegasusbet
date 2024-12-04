package webcrawler

class CrawlerController {

    CrawlerService crawlerService

    def horses() {
        try {
            crawlerService.fetchHorses()

            respond ([ "message": "Dados obtidos com sucesso" ])
        } catch (Exception e) {
            response.status = 500

            respond( ["message": "Falha ao obter dados dos cavalos"])
        }
    }

    def jockeys() {
        try {
            crawlerService.fetchJockeys()

            respond([ "message": "Dados obtidos com sucesso" ])
        } catch (Exception e) {
            response.status = 500

            respond( ["message": "Falha ao obter dados dos jockeys"])
        }
    }

    def racecourses() {
        try {
            crawlerService.fetchRaceCourses()

            respond ([ "message": "Dados obtidos com sucesso" ])
        } catch (Exception e) {
            response.status = 500

            respond( ["message": "Falha ao obter dados dos circuitos"] )
        }

    }

}
