import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuItem } from "primeng/api";
import { Subject, takeUntil } from "rxjs";
import { RacesService } from "../../../../../services/races/races.service";
import { Race } from "../../../../../models/races/Race";

@Component({
  selector: 'app-races-page',
  templateUrl: './races-page.component.html',
  styleUrls: ['./races-page.component.css']
})
export class RacesPageComponent implements OnInit, OnDestroy{
  private destroy$ = new Subject<void>()

  races: Race[] = []

  constructor(private racesService: RacesService) {
  }

  ngOnInit(): void {
    this.fetchRaces()
  }

  fetchRaces(): void {
    this.racesService.fetchRaces(0, 1000)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          if (response) {
            this.races = response.races
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
