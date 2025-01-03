import { Horse } from "./Horse";

export interface GetHorsesResponse {
  horses: Horse[]
  items: number
  offsetItems: number
}
