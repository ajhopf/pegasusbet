export interface RaceResult {
    raceId: number
    positions: {
        raceHorseJockeyId: number
        position: number
        result?: string
        odds: number}[]
}