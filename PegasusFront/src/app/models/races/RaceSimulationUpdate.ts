export interface RaceSimulationUpdate {
  raceId: number
  positions: {
    raceHorseJockeyId: number
    position: number
    odds: number
  }[]
}
