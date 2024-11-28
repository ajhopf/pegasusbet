package webcrawler

class SportingLifeCrawlerController {

    SportingLifeCrawlerService sportingLifeCrawlerService

    def index() { }

    def list() {
        sportingLifeCrawlerService.fetchRaces()

        respond ([ "message": "Dados obtidos com sucesso" ])
    }
}
