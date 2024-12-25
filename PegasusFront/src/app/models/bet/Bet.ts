export interface Bet {
  id: number
  amount: number
  raceHorseJockeyId: number
  timeStamp: Object
  status: 'WAITING' | 'WIN' | 'LOSS'
}
