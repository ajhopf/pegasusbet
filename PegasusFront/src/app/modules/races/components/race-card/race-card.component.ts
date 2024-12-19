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

  ref: DynamicDialogRef | undefined;

  sideBarVisible = false
  selectedHorseJockey: RaceHorseJockey | undefined

  constructor(public dialogService: DialogService) {}

  showInformation (id: number, type: string) {
    this.ref = this.dialogService.open(InformationOverlayComponent, {
        data: { id: id, type: type },
        width: '50%',
        contentStyle: { overflow: 'auto' },
        baseZIndex: 10000
    }
    );
  }

  toggleSideBar(horseJockey: RaceHorseJockey) {
    this.selectedHorseJockey = horseJockey
    this.sideBarVisible = true
  }


}
