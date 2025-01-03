import { Race } from "./Race";

export interface GetRacesResponse {
  races: Race[]
  items: number
  offsetItems: number
}
