package pegasusdatastore

class HorseController {
    HorseService horseService

    def horse() {
        Horse horse = new Horse(
                numberOfVictories: 1,
                numberOfRaces: 1,
                name: 'horse',
                sex: 'sex',
                lastResults: ['1', '2', '3'],
                age: "10"
        )

        horseService.addHorse(horse)
    }
}
