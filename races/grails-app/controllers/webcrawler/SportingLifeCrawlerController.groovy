package webcrawler

class SportingLifeCrawlerController {

    SportingLifeCrawlerService sportingLifeCrawlerService

    def index() { }

    def list() {
        sportingLifeCrawlerService.fetchRaces()

        respond ([ "id": 1 ])
    }
}
