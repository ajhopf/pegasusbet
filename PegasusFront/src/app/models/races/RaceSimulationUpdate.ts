export interface RaceSimulationUpdate {
  raceId: number
  finished: boolean
  positions: {
    raceHorseJockeyId: number
    position: number
    odds: number
  }[]
}
