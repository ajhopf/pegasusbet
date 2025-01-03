import { Results } from "../Results";

export interface Horse {
  id: number
  name: string
  age: string
  sex: string
  numberOfRaces?: number
  numberOfVictories?: number
  horseResults?: Results[]
}
