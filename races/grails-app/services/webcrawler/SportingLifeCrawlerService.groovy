package webcrawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import grails.gorm.transactions.Transactional
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import races.Horse
import races.HorseService
import races.Jockey
import races.JockeyService
import races.RaceCourse
import races.RaceCourseService
import races.RaceService

@Transactional
class SportingLifeCrawlerService {
    RaceCourseService raceCourseService
    RaceService raceService
    HorseService horseService
    JockeyService jockeyService

    String BASE_URL = "https://www.sportinglife.com"

    void fetchRaces() {
        println "fetching information..."
        Document racecardsPage = Jsoup.connect("$BASE_URL/racing/racecards").get()

        Elements raceCards = racecardsPage.select("[data-test-id=racecards-next-off-race]")

        raceCards.each {card ->
            Element link = card.select("a").first()
            String linkHref = link.attr("href")

            getRaceInfos(linkHref)
        }

        println "information fetched successfully!"
    }

    void getRaceInfos(String url) {
        Document racePage = Jsoup.connect("$BASE_URL$url").get()

        Element title = racePage.select("p.CourseListingHeader__StyledMainTitle-sc-164fi8g-5").first()
        Element subtitle = racePage.select("p.CourseListingHeader__StyledMainSubTitle-sc-164fi8g-7").first()

        String raceCourse = title.text().split(" ")[1]
        String raceTime = title.text().split(" ")[0]
        String date = subtitle.text()

        RaceCourse raceCourseInstance = raceCourseService.addRaceCourse(raceCourse)
        raceService.addRace(raceCourseInstance, date, raceTime)

        Elements runnerCard = racePage.select("[data-test-id=runner]")

        runnerCard.each {runner ->
            Element horseLink = runner.select("[data-test-id=horse-name-link]").first()
            String horseLinkHref = horseLink.attr("href")

            getHorseInfo(horseLinkHref)

            Elements subinfo = runner.select("[data-test-id=horse-sub-info]")
            Element jockeyLink = subinfo.select("a").first()
            String jockeyLinkHref = jockeyLink.attr("href")

            getJockeyInfo(jockeyLinkHref)
        }

    }

    void getHorseInfo(String url) {
        Document horsePage = Jsoup.connect("$BASE_URL$url").get()
        Element horseInfoCard = horsePage.select("div.HorseBackgroundWrapper-sc-1exxuzc-0").first()

        String name = horseInfoCard.selectFirst("h1").text()

        Element sexRow = horseInfoCard.select("tr:has(th:contains(Sex))").first()
        String sex = sexRow.select("td").text()

        Element ageRow = horseInfoCard.select("tr:has(th:contains(Age))").first()
        String age = ageRow.select("td").text()

        Map results = getResults(horsePage)

        Horse horse = new Horse(
                name: name,
                age: age,
                sex: sex,
                numberOfRaces: results.numberOfRaces,
                numberOfVictories: results.numberOfVictories as int,
                lastResults: results.lastThreeResults as List<String>
        )

        horseService.addHorse(horse)
    }

    void getJockeyInfo(String url) {
        Document jockeyPage = Jsoup.connect("$BASE_URL$url").get()
        Element jockeyInfoCard = jockeyPage.select("div.HorseBackgroundWrapper-sc-1exxuzc-0").first()

        String name = jockeyInfoCard.selectFirst("h1").text()
        Map results = getResults(jockeyPage)

        Jockey jockey = new Jockey(
                name: name,
                numberOfRaces: results.numberOfRaces,
                numberOfVictories: results.numberOfVictories as int,
                lastResults: results.lastThreeResults as List<String>
        )

        jockeyService.addJockey(jockey)
    }

    Map getResults(Document page) {
        Element resultsTable = page.select("table.FormTable__StyledTable-sc-1xr7jxa-1").first()
        Element resultsTableBody = resultsTable.select("tbody").first()
        Elements resultsTableBodyLines = resultsTableBody.select("tr")

        int numberOfRaces = resultsTableBodyLines.size()

        int numberOfVictories = 0

        resultsTableBodyLines.each {line ->
            Elements tableCells = line.select("td")
            String position = tableCells[1].text()

            if (position.split("/")[0] == "1") {
                numberOfVictories++
            }
        }

        List lastThreeResults = []

        for (i in 0..2) {
            if (resultsTableBodyLines[i] != null) {
                Elements tableLineCells = resultsTableBodyLines[i]?.select("td")
                if (tableLineCells[1] != null) {
                    String result = tableLineCells[1].text()
                    lastThreeResults << result
                }

            }
        }

        return [
                numberOfRaces: numberOfRaces,
                numberOfVictories: numberOfVictories,
                lastResults: lastThreeResults
        ]
    }
}
