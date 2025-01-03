import { Jockey } from "./Jockey";

export interface GetJockeysResponse {
  jockeys: Jockey[]
  items: number
  offsetItems: number
}
