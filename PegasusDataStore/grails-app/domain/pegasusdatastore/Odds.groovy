package pegasusdatastore

class Odds {
    RaceHorseJockey raceHorseJockey
    Double probability // Probabilidade inicial
    Double initialOdd // Odd inicial
    Double currentOdd // Odd ajustada
    Date lastUpdated = new Date()

    static constraints = {
        probability nullable: false
        initialOdd nullable: false
        currentOdd nullable: false
    }

    static mapping = {
        lastUpdated column: 'last_updated'
    }

    static belongsTo = [raceHorseJockey: RaceHorseJockey] // Odds pertence a RaceHorseJockey
}
