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

  showOnlyRacesThatAcceptBetting: boolean = false
  showOnlyRacesThatUserBetted: boolean = false

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
    this.filteredRaces = []

    if (this.showOnlyRacesThatUserBetted) {
      this.userBets.forEach(bet => {
        const race = this.findRaceByRaceHorseJockeyId(bet.raceHorseJockeyId)

        if (race) {
          !this.filteredRaces.includes(race) && this.filteredRaces.push(race)
        }
      })
    } else {
      this.filteredRaces = this.races
    }

    if (!this.showOnlyRacesThatAcceptBetting) {
      this.filteredRaces = this.filteredRaces.filter(r => !r.finished)
    }
  }

  findRaceByRaceHorseJockeyId(id: number): Race | undefined {
    return this.races.find(r => r.raceHorseJockeys.find(rhj => rhj.id === id))
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
