export interface StartRace {
    type: string,
    raceId: number,
    participants: {
        raceHorseJockeyId: number
        odds: number
    }[]
}