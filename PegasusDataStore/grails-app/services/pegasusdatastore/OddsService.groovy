package pegasusdatastore

import dao.OddsDAO
import dao.RaceHorseJockeyDAO
import grails.gorm.transactions.Transactional
import model.dtos.oddsDTOs.OddsResponseDTO
import model.mappers.OddsMapper

@Transactional
class OddsService {
    private final victoriesWeight = 2

    OddsDAO oddsDAO
    RaceHorseJockeyDAO raceHorseJockeyDAO

    List<OddsResponseDTO> list(Map params) {
        List<Odds> oddsList = oddsDAO.listOdds(params)

        return OddsMapper.toDTOs(oddsList)
    }

    OddsResponseDTO getOddsByRaceHorseJockey(Long id) {
        RaceHorseJockey raceHorseJockey = raceHorseJockeyDAO.getRaceHorseJockeyById(id)

        Odds odds = oddsDAO.findByRaceHorseJockey(raceHorseJockey)

        return OddsMapper.toResponseDTO(odds)
    }

    Map<Long, Double> calculateInitialOdds(List<RaceHorseJockey> raceHorseJockeyList) {
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
            RaceHorseJockey raceHorseJockey = raceHorseJockeyDAO.getRaceHorseJockeyById(it.key)

            Odds raceHorseJockeyOdds = new Odds()
            raceHorseJockeyOdds.raceHorseJockey = raceHorseJockey
            raceHorseJockeyOdds.rating = jockeyRatingMap.find {it.key == raceHorseJockey.id}.value
            raceHorseJockeyOdds.initialOdd = it.value
            raceHorseJockeyOdds.currentOdd = it.value
//
//            raceHorseJockeyOdds.save(flush: true)
            oddsDAO.saveOdds(raceHorseJockeyOdds)
        }

        return initialOddsMap
    }

    void recalculateOdds(Long raceId) {
        Race race = Race.get(raceId)
        List<RaceHorseJockey> raceHorseJockeyList = RaceHorseJockey.findAllByRace(race)

        BigDecimal raceTotalBetsAmount = 0

        raceHorseJockeyList.each {
            raceTotalBetsAmount += it.totalBetsAmount
        }

        if (raceTotalBetsAmount > 0) {
            raceHorseJockeyList.each { RaceHorseJockey raceHorseJockey ->
                // Total apostado no cavalo específico
                BigDecimal horseBetAmount = raceHorseJockey.totalBetsAmount

                // Proporção das apostas
                double betProportion = horseBetAmount / raceTotalBetsAmount

                // Nova odd baseada na proporção
                double newOdd = (betProportion > 0) ? (1 / betProportion) * 0.9 : raceHorseJockey.odds.currentOdd // 0.9 é a margem da casa

                // Atualizar a odd
                Odds odds = Odds.findByRaceHorseJockey(raceHorseJockey)
                if (odds) {
                    odds.currentOdd = newOdd
                    odds.save(flush: true)
                }
            }
        }
    }

    //Quanto maior o rating melhor é o cavalo
    double calculateHorseRating(Horse horse) {
        double victoriesIndex = horse.numberOfVictories + 1
        double averagePosition = calculateAveragePosition(horse)

        return (victoriesIndex * this.victoriesWeight) / averagePosition
    }

    double calculateAveragePosition(Horse horse) {
        if (!horse.horseResults || horse.horseResults.isEmpty()) {
            return 20
        }

        double sumOfPositions = 0
        double validPositions = 0

        horse.horseResults.each { horseResult ->
            List<String> resultParts = horseResult.result.split("/")
            if (resultParts.size() == 2) {
                int position = resultParts[0]?.toInteger() ?: 0
                sumOfPositions += position
                ++validPositions
            }
        }

        return (sumOfPositions / validPositions)
    }
}
