import { Component, Input, OnInit } from '@angular/core';
import { Race } from "../../../../models/races/Race";
import { HorseService } from "../../../../services/horse/horse.service";
import { Horse } from "../../../../models/horse/Horse";
import { Jockey } from "../../../../models/jockey/Jockey";
import { forkJoin, take } from "rxjs";
import { JockeyService } from "../../../../services/jockey/jockey.service";

@Component({
  selector: 'app-races-cards',
  templateUrl: './races-cards.component.html',
  styleUrls: ['./races-cards.component.css']
})
export class RacesCardsComponent implements OnInit{
  @Input() race!: Race

  horses: Horse[] = []
  jockeys: Jockey[] = []

  constructor(
    protected horseService: HorseService,
    protected jockeyService: JockeyService
    ) {
  }

  ngOnInit() {
    this.fetchHorses()
    this.fetchJockeys()
  }

  fetchHorses() {
    const horseIds =
      this.race.raceHorseJockeys.map(raceHorseJockey => raceHorseJockey.horseId)

    const horseObservables =
      horseIds.map(id => this.horseService.getHorseById(id).pipe(take(1)))

    forkJoin(horseObservables).subscribe({
      next: (horseResponses) => {
        this.horses = horseResponses.map(response => response.horse)
        console.log(this.horses)
      },
      error: (err) => {
        console.error('Failed to load horses:', err)
      }
    });
  }

  fetchJockeys() {
    const jockeyIds =
      this.race.raceHorseJockeys.map(raceHorseJockey => raceHorseJockey.jockeyId)

    const jockeyObservables =
      jockeyIds.map(id => this.jockeyService.getJockeyById(id).pipe(take(1)))

    forkJoin(jockeyObservables).subscribe({
      next: (jockeyResponses) => {
        this.jockeys = jockeyResponses.map(response => response.jockey)
      },
      error: (err) => {
        console.log('Failed to load jockey', err)
      }
    })
  }

  getHorseInfo(id: number) {
    return  this.horses.find(horse => horse.id === id)
  }

  getJockeyInfo(id: number) {
    return  this.jockeys.find(jockey => jockey.id === id)
  }

}
