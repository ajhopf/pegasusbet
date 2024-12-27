export interface CreateBetRequest {
  amount: number
  raceHorseJockeyId: number
  betType: 'WIN' | 'PLACE' | 'SHOW'
}
