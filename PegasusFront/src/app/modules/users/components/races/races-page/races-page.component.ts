import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from "primeng/api";
import { Subject, take, takeUntil } from "rxjs";
import { RacesService } from "../../../../../services/races/races.service";
import { Race } from "../../../../../models/races/Race";
import { Bet } from "../../../../../models/bet/Bet";
import { BetService } from "../../../../../services/bet/bet.service";

@Component({
  selector: 'app-races-page',
  templateUrl: './races-page.component.html',
  styleUrls: ['./races-page.component.css']
})
export class RacesPageComponent implements OnInit, OnDestroy{
  private destroy$ = new Subject<void>()

  races: Race[] = []
  userBets: Bet[] = []
  filteredRaces: Race[] = []

  showAll: boolean = false

  constructor(
    private racesService: RacesService,
    private betsService: BetService
  ) {
  }

  ngOnInit(): void {
    this.fetchRaces()
    this.fetchUserBets()
  }

  handleFilterRaces() {
    if (!this.showAll) {
      this.filteredRaces = this.races.filter(r => !r.finished)
    } else {
      this.filteredRaces = this.races
    }
  }

  fetchUserBets() {
    this.betsService.getUserBets()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: value => {
          this.userBets = value.bets
        },
        error: err => console.log(err)
      })
  }

  fetchRaces(): void {
    this.racesService.fetchRaces(0, 1000)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          if (response) {
            this.races = response.races
            this.handleFilterRaces()
            this.sortRacesByDate(this.races)
            this.sortRaceHorseJockeys(this.races)
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  sortRacesByDate(races: Race[]) {
    races.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime())
  }

  sortRaceHorseJockeys(races: Race[]) {
    races.forEach(race => {
      race.raceHorseJockeys.sort((a, b) => a.odds.currentOdd - b.odds.currentOdd)
    })
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }

}
