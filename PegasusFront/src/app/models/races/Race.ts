import { RaceHorseJockey } from "./RaceHorseJockey";

export interface Race {
  id: number,
  raceCourseId: number,
  time: string
  date: string
  raceHorseJockeys: RaceHorseJockey[]
}
