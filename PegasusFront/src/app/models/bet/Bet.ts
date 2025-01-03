export interface Bet {
  id: number
  amount: number
  raceHorseJockeyId: number
  timeStamp: Object
  resultViewed?: boolean
  betType: 'WIN' | 'PLACE' | 'SHOW'
  status: 'WAITING' | 'WIN' | 'LOSS'
}
