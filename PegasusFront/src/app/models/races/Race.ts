import { RaceHorseJockey } from "./RaceHorseJockey";
import { RaceCourse } from "../race-course/RaceCourse";

export interface Race {
  id: number,
  raceCourse: RaceCourse,
  time: string
  date: string
  raceHorseJockeys: RaceHorseJockey[]
}
