import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig } from "primeng/dynamicdialog";
import { HorseService } from "../../../../services/horse/horse.service";
import { JockeyService } from "../../../../services/jockey/jockey.service";
import { Jockey } from "../../../../models/jockey/Jockey";
import { Horse } from "../../../../models/horse/Horse";
import { take } from "rxjs";

@Component({
  selector: 'app-information-overlay',
  templateUrl: './information-overlay.component.html',
  styleUrls: ['./information-overlay.component.css']
})
export class InformationOverlayComponent implements OnInit {
  type: 'jockey' | 'horse' = 'horse'

  horse: Horse | undefined
  jockey: Jockey | undefined

  constructor(
    private dynamicDialog: DynamicDialogConfig,
    private horseService: HorseService,
    private jockeyService: JockeyService
  ) {}

  ngOnInit() {
    const id = this.dynamicDialog.data.id
    this.type = this.dynamicDialog.data.type

    if (this.type == 'jockey') {
      this.fetchjockeyInfos(id)
    } else {
      this.fetchHorseInfos(id)
    }

  }

  fetchjockeyInfos(id: number) {
    this.jockeyService.getJockeyById(id)
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.jockey = value.jockey
          this.dynamicDialog.header = "Jockey: " + value.jockey.name
        }
      })
  }

  fetchHorseInfos(id: number) {
    this.horseService.getHorseById(id)
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.horse = value.horse
          this.dynamicDialog.header = "Cavalo: " + value.horse.name
        }
      })
  }
}
