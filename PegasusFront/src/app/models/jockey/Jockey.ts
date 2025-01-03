import { Results } from "../Results";

export interface Jockey {
  id: number
  name: string
  numberOfRaces?: number
  numberOfVictories?: number
  jockeyResults?: Results[]
}
