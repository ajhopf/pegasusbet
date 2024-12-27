import { Component, Input } from '@angular/core';
import { Race } from "../../../../models/races/Race";
import { DialogService, DynamicDialogRef } from "primeng/dynamicdialog";
import { InformationOverlayComponent } from "../information-overlay/information-overlay.component";
import { RaceHorseJockey } from "../../../../models/races/RaceHorseJockey";

@Component({
  selector: 'app-race-card',
  templateUrl: './race-card.component.html',
  styleUrls: ['./race-card.component.css']
})
export class RaceCardComponent {
  @Input() race!: Race

  sideBarVisible = false
  selectedHorseJockey: RaceHorseJockey | undefined

  constructor() {}

  toggleSideBar(horseJockey: RaceHorseJockey) {
    this.selectedHorseJockey = horseJockey
    this.sideBarVisible = true
  }


}
