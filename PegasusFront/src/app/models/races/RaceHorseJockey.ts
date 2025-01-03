import { Jockey } from "../jockey/Jockey";
import { Horse } from "../horse/Horse";
import { Odds } from "../odds/Odds";

export interface RaceHorseJockey {
  id: number
  number: number
  jockey: Jockey
  horse: Horse
  position?: string
  raceTime?: string
  odds: Odds
}
