import { Component, Input, OnInit } from '@angular/core';
import { RaceSimulationUpdate } from "../../../../../../../models/races/RaceSimulationUpdate";
import { Race } from "../../../../../../../models/races/Race";

@Component({
  selector: 'app-race-finished',
  templateUrl: './race-finished.component.html',
  styleUrls: ['./race-finished.component.css']
})
export class RaceFinishedComponent implements OnInit{
  @Input() raceFinalPositions: RaceSimulationUpdate | undefined
  @Input() race: Race | undefined

  ngOnInit(): void {
    this.raceFinalPositions?.positions?.sort(
      (a, b) => {
        return  b.position - a.position
      }
    )
  }

  getHorse(id:number) {
    const index = this.race!.raceHorseJockeys.findIndex(rhj => rhj.id === id)
    return this.race!.raceHorseJockeys[index].horse
  }

  getJockey(id:number) {
    const index = this.race!.raceHorseJockeys.findIndex(rhj => rhj.id === id)
    return this.race!.raceHorseJockeys[index].jockey
  }
}
