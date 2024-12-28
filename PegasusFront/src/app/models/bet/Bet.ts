export interface Bet {
  id: number
  amount: number
  raceHorseJockeyId: number
  timeStamp: Object
  betType: 'WIN' | 'PLACE' | 'SHOW'
  status: 'WAITING' | 'WIN' | 'LOSS'
}
