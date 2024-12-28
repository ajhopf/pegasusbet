import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Race } from "../../../../models/races/Race";
import { DialogService, DynamicDialogRef } from "primeng/dynamicdialog";
import { InformationOverlayComponent } from "../information-overlay/information-overlay.component";
import { RaceHorseJockey } from "../../../../models/races/RaceHorseJockey";
import { BetService } from "../../../../services/bet/bet.service";
import { take } from "rxjs";
import { Bet } from "../../../../models/bet/Bet";

@Component({
  selector: 'app-race-card',
  templateUrl: './race-card.component.html',
  styleUrls: ['./race-card.component.css']
})
export class RaceCardComponent{
  @Input() race!: Race
  @Input() userBets: Bet[] | undefined
  @Output() betCreated: EventEmitter<any> = new EventEmitter<any>()

  sideBarVisible = false
  selectedHorseJockey: RaceHorseJockey | undefined

  toggleSideBar(horseJockey: RaceHorseJockey) {
    this.selectedHorseJockey = horseJockey
    this.sideBarVisible = true
  }



  findBetByRaceHorseJockey(raceHorseJockeyId: number) {
    if (this.userBets) {
      const bet = this.userBets.filter(bet => {
        return bet.raceHorseJockeyId == raceHorseJockeyId
      })

      return bet
    } else {
      return undefined
    }
  }

}
