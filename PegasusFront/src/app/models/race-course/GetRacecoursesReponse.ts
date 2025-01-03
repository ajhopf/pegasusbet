import { RaceCourse } from "./RaceCourse";

export interface GetRacecoursesResponse {
  raceCourses: RaceCourse[]
  items: number
  offsetItems: number
}
