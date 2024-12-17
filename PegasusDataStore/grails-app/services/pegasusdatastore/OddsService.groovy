package pegasusdatastore

import grails.gorm.transactions.Transactional

@Transactional
class OddsService {
    private final victoriesWeight = 0.6
    private final averagePositionWeight = 0.4
    private final horseWeight = 0.7
    private final jockeyWeight = 0.3




    def calculateInitialOdds(RaceHorseJockey raceHorseJockey) {
        Horse horse = raceHorseJockey.horse
        Jockey jockey = raceHorseJockey.jockey

        double horseAveragePosition = calculateAveragePosition(horse.horseResults)
        double jockeyAveragePosition = calculateAveragePosition(jockey.jockeyResults)

        double horseProbability = calculateProbability(horse.numberOfVictories, horse.numberOfRaces, horseAveragePosition)
        double jockeyProbability = calculateProbability(jockey.numberOfVictories, jockey.numberOfRaces, jockeyAveragePosition)

        double sumOfProbabilities = (horseWeight * horseProbability) + (jockeyWeight * jockeyProbability)

        if (sumOfProbabilities <= 0) {
            sumOfProbabilities = 0.01
        }

        double initialOdd = 1 / sumOfProbabilities

        new Odds(
                raceHorseJockey: raceHorseJockey,
                probability: sumOfProbabilities,
                initialOdd: initialOdd,
                currentOdd: initialOdd
        ).save(flush: true)
    }

    private double calculateProbability(int numberOfVictories, int numberOfRaces, double averagePosition) {
        double victoriesProbability = (double) (victoriesWeight * (numberOfRaces > 0 ? numberOfVictories / numberOfRaces : 0))
        double averagePositionProbability = averagePositionWeight * (averagePosition)

        return victoriesProbability + averagePositionProbability
    }

    private double calculateAveragePosition(Set results) {
        if (!results || results.isEmpty()) {
            return Double.MAX_VALUE
        }

        // Somar todas as posições extraindo do formato "posição/número de competidores"
        double totalPosition = 0
        int count = 0

        results.each { result ->
            String[] parts = result.split("/") // Divide "posição/número de competidores"
            if (parts.size() == 2) {
                int position = parts[0]?.toInteger() ?: 0
                totalPosition += position
                count++
            }
        }

        return count > 0 ? (totalPosition / count) : Double.MAX_VALUE
    }
}
