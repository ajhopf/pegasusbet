export interface Bet {
  id: number
  amount: number
  raceHorseJockeyId: number
  timeStamp: Object
  viewed?: boolean
  betType: 'WIN' | 'PLACE' | 'SHOW'
  status: 'WAITING' | 'WIN' | 'LOSS'
}
