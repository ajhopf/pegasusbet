import { Component, OnInit } from '@angular/core'
import { RaceSimulationService } from '../../../../../../services/race-simulation/race-simulation.service'
import { Race } from '../../../../../../models/races/Race'
import { Observable } from 'rxjs'
import { RacesService } from '../../../../../../services/races/races.service'
import { GetRacesResponse } from '../../../../../../models/races/GetRacesResponse'
import { RaceSimulationParticipant } from '../../../../../../models/races/RaceSimulationParticipant'
import {  RaceSimulationUpdate } from '../../../../../../models/races/RaceSimulationUpdate'
import { CookieService } from 'ngx-cookie-service'
import { TokenFields } from '../../../../../../models/auth/TokenFields'
import { MessageService } from "primeng/api";

@Component({
  selector: 'app-start-race-simulation',
  templateUrl: './start-race-simulation.component.html',
  styleUrls: ['./start-race-simulation.component.css']
})
export class StartRaceSimulationComponent {
  races$: Observable<GetRacesResponse> | undefined
  raceUpdates: RaceSimulationUpdate = {raceId: 0, finished: false, positions: []}
  raceIsLive = false

  constructor(
    private raceSimulationService: RaceSimulationService,
    private raceService: RacesService,
    private messageService: MessageService
  ) {
    this.races$ = this.raceService.fetchRaces(0, 1000)

    this.connectWebSocket()
  }

  connectWebSocket(): void {
    this.raceSimulationService.connect()

    this.raceSimulationService.onWebSocketUpdate((data) => {
      if (data.finished) {
        this.raceIsLive = false
        this.messageService.add({
          severity: 'success',
          summary: 'Corrida finalizada'
        })
        this.races$ = this.raceService.fetchRaces(0, 1000)
      } else {
        this.raceIsLive = true
      }
    })
  }

  startRace(race: Race): void {
    const participants: RaceSimulationParticipant[] = race.raceHorseJockeys.map(rhj => {
      return {raceHorseJockeyId: rhj.id, odds: rhj.odds.currentOdd}
    })

    this.raceSimulationService.startRace(race.id, participants); // Envia dados ao backend
  }

}
