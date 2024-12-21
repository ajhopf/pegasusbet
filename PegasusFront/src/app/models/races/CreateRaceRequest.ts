export interface HorseJockey {
  horseId: number
  jockeyId: number
}

export interface CreateRaceRequest {
  raceCourseId: number
  time: string
  date: string
  horseJockeys: HorseJockey[]
}
