import { Component, OnInit } from '@angular/core'
import { map, Observable, take } from 'rxjs'
import { GetUserBetsResponse } from '../../../../../models/bet/GetUserBetsResponse'
import { BetService } from '../../../../../services/bet/bet.service'
import { OddsService } from '../../../../../services/odds/odds.service'
import { MessageService } from 'primeng/api'
import { RacesService } from '../../../../../services/races/races.service'
import { Race } from '../../../../../models/races/Race'
import { InformationOverlayComponent } from '../../information-overlay/information-overlay.component'
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog'

@Component({
  selector: 'app-bets-table',
  templateUrl: './bets-table.component.html',
  styleUrls: ['./bets-table.component.css']
})
export class BetsTableComponent implements OnInit {
  bets$: Observable<GetUserBetsResponse> | undefined
  races: Race[] = []

  constructor(
    private betService: BetService,
    private raceService: RacesService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.bets$ = this.betService.getUserBets()

    this.bets$?.pipe(
      map(response => response.bets.map(bet => bet.raceHorseJockeyId)),
    ).subscribe({
      next: ids => {
        this.fetchRaceInfo(ids)
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Falha ao carregar apostas.',
        });
      }
    });
  }

  private fetchRaceInfo(ids: number[]): void {
    ids.forEach(id => {
      this.raceService.fetchRaceByRaceHorseJockeyId(id)
        .pipe(take(1))
        .subscribe({
          next: data => {
            this.races.push(data.race)
          },
          error: err => {
            this.messageService.add({
              severity: 'error',
              summary: 'Falha ao buscar informação para o id ' + id,
            });
          }
        });
    });
  }

  getRaceByHorseJockeyId(id: number): Race | undefined {
    const index = this.races.findIndex(
      race => race.raceHorseJockeys.some(
        raceHorseJockey => raceHorseJockey.id === id
      )
    )

    return this.races[index]
  }

}
