package pegasusdatastore

import grails.gorm.transactions.Transactional
import model.dtos.oddsDTOs.OddsResponseDTO
import model.mappers.OddsMapper

@Transactional
class OddsService {
    private final victoriesWeight = 2

    List<OddsResponseDTO> list(Map params) {
        List<Odds> oddsList = Odds.list(offset: params.offset, max: params.max)

        return OddsMapper.toDTOs(oddsList)
    }

    void calculateInitialOdds(List<RaceHorseJockey> raceHorseJockeyList) {
        List<Double> predefinedInitialOdds = [2.0, 2.5, 3.0, 4.0, 5.0, 6.0, 7.0]

        Map<Long, Double> jockeyRatingMap = [:]

        raceHorseJockeyList.each { RaceHorseJockey it ->
            Double rating = calculateHorseRating(it.horse)
            jockeyRatingMap[it.id] = rating
        }

        Map<Long, Double> sortedJockeyRatingMap = jockeyRatingMap.sort { -it.value }

        Map<Long, Double> initialOddsMap = [:]

        sortedJockeyRatingMap.eachWithIndex { it, index ->
            double odd = (index < predefinedInitialOdds.size()) ? predefinedInitialOdds[index] : predefinedInitialOdds.last()
            initialOddsMap[it.key] = odd
        }

        initialOddsMap.eachWithIndex{ it, index ->
            RaceHorseJockey raceHorseJockey = RaceHorseJockey.get(it.key)

            Odds raceHorseJockeyOdds = new Odds()
            raceHorseJockeyOdds.raceHorseJockey = raceHorseJockey
            raceHorseJockeyOdds.rating = jockeyRatingMap.find {it.key == raceHorseJockey.id}.value
            raceHorseJockeyOdds.initialOdd = it.value
            raceHorseJockeyOdds.currentOdd = it.value

            raceHorseJockeyOdds.save(flush: true)
        }


    }

    double calculateHorseRating(Horse horse) {
        double victoriesIndex = (double) (victoriesWeight * (horse.numberOfRaces > 0 ? horse.numberOfVictories / horse.numberOfRaces : 0))
        double averagePosition = calculateAveragePosition(horse)

        return victoriesIndex + averagePosition
    }

    double calculateAveragePosition(Horse horse) {
        if (!horse.horseResults || horse.horseResults.isEmpty()) {
            return 12
        }

        double sumOfPositions = 0

        horse.horseResults.each { horseResult ->
            List<String> resultParts = horseResult.result.split("/")
            if (resultParts.size() == 2) {
                int position = resultParts[0]?.toInteger() ?: 0
                sumOfPositions += position
            }
        }

        return (sumOfPositions / horse.numberOfRaces)
    }
}
