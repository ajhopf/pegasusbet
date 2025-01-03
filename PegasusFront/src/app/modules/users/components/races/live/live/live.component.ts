import { Component, OnDestroy, OnInit } from '@angular/core'

import { RacesService } from '../../../../../../services/races/races.service'
import { Race } from '../../../../../../models/races/Race'
import { take } from 'rxjs'
import { RaceSimulationUpdate } from '../../../../../../models/races/RaceSimulationUpdate'
import { RaceSimulationService } from "../../../../../../services/race-simulation/race-simulation.service";
import { NotificationsService } from "../../../../../../services/notification/notifications.service";

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
    private raceService: RacesService,
    private notificationService: NotificationsService
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

      if (this.raceUpdates.finished) {
        setTimeout(() => {
          this.notificationService.triggerRefreshNotifications()
        },5000)
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

  getColor(number: string) {
    const colors = [
      "#FF5733", // Vermelho alaranjado
      "#33FF57", // Verde limão
      "#3357FF", // Azul forte
      "#F1C40F", // Amarelo
      "#9B59B6", // Roxo
      "#1ABC9C", // Verde água
      "#E74C3C", // Vermelho
      "#2980B9", // Azul médio
      "#8E44AD", // Roxo escuro
      "#2ECC71", // Verde esmeralda
      "#E67E22", // Laranja
      "#34495E", // Azul acinzentado
      "#F39C12", // Amarelo dourado
      "#16A085", // Verde esverdeado
      "#C0392B", // Vermelho escuro
      "#27AE60", // Verde floresta
      "#D35400", // Laranja escuro
      "#2C3E50", // Azul petróleo
      "#BDC3C7", // Cinza claro
      "#7F8C8D"  // Cinza escuro
    ];

    if (colors.length < +number) {
      return colors[+number]
    } else {
      return colors[19]
    }
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
