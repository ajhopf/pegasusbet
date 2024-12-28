import { Component, OnDestroy, OnInit } from '@angular/core'
import { RaceSimulationService } from '../../../../../../services/race-simulation/race-simulation.service'
import { RacesService } from '../../../../../../services/races/races.service'
import { Race } from '../../../../../../models/races/Race'
import { take } from 'rxjs'
import { RaceSimulationUpdate } from '../../../../../../models/races/RaceSimulationUpdate'

@Component({
  selector: 'app-live',
  templateUrl: './live.component.html',
  styleUrls: ['./live.component.css']
})
export class LiveComponent implements OnInit, OnDestroy {
  raceUpdates: RaceSimulationUpdate = { raceId: 0, finished: false, positions: [] }
  currentRace: Race | undefined
  noLiveRace=  false

  constructor(
    private raceSimulationService: RaceSimulationService,
    private raceService: RacesService
  ) { }

  ngOnInit(): void {
    this.connectWebSocket()
    this.showNoCurrentRace()
  }

  showNoCurrentRace() {
    setTimeout(() => {
      if(!this.currentRace) {
        this.noLiveRace = true
      }
    }, 4000)
  }

  connectWebSocket(): void {
    this.raceSimulationService.connect()

    this.raceSimulationService.onRaceUpdate((data: RaceSimulationUpdate) => {
      this.raceUpdates = { raceId: data.raceId, finished: data.finished, positions: data.positions };
      console.log(this.raceUpdates);

      if (this.raceUpdates.finished) {

      }

      if (!this.currentRace) {
        this.raceService.fetchRaceByRaceHorseJockeyId(data.positions[0].raceHorseJockeyId)
          .pipe(take(1))
          .subscribe({
            next: data => {
              this.currentRace = data.race
            }
          })
      }

    })
  }

  handleRaceFinish() {

  }

  getColor() {

  }

  getHorse(id:number) {
    const index = this.currentRace!.raceHorseJockeys.findIndex(rhj => rhj.id === id)
    return this.currentRace!.raceHorseJockeys[index].horse
  }

  getJockey(id:number) {
    const index = this.currentRace!.raceHorseJockeys.findIndex(rhj => rhj.id === id)
    return this.currentRace!.raceHorseJockeys[index].jockey
  }

  ngOnDestroy(): void {
    this.raceSimulationService.closeConnection()
  }


}
