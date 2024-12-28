import { Component, OnDestroy, OnInit } from '@angular/core'

import { RacesService } from '../../../../../../services/races/races.service'
import { Race } from '../../../../../../models/races/Race'
import { take } from 'rxjs'
import { RaceSimulationUpdate } from '../../../../../../models/races/RaceSimulationUpdate'
import { RaceSimulationService } from "../../../../../../services/race-simulation/race-simulation.service";

@Component({
  selector: 'app-live',
  templateUrl: './live.component.html',
  styleUrls: ['./live.component.css']
})
export class LiveComponent implements OnInit {
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

    this.raceSimulationService.onWebSocketUpdate((data: RaceSimulationUpdate) => {
      this.raceUpdates = { raceId: data.raceId, finished: data.finished, positions: data.positions };
      console.log(this.raceUpdates);

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

  // ngOnDestroy(): void {
  //   this.raceSimulationService.closeConnection()
  // }


}
