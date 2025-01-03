import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig } from "primeng/dynamicdialog";
import { HorseService } from "../../../../services/horse/horse.service";
import { JockeyService } from "../../../../services/jockey/jockey.service";
import { Jockey } from "../../../../models/jockey/Jockey";
import { Horse } from "../../../../models/horse/Horse";
import { take } from "rxjs";
import { Results } from '../../../../models/Results'

@Component({
  selector: 'app-information-overlay',
  templateUrl: './information-overlay.component.html',
  styleUrls: ['./information-overlay.component.css']
})
export class InformationOverlayComponent implements OnInit {
  type: 'jockey' | 'horse' = 'horse'

  horse: Horse | undefined
  jockey: Jockey | undefined
  lastResultsAverage: number | null = null

  constructor(
    private dynamicDialog: DynamicDialogConfig,
    private horseService: HorseService,
    private jockeyService: JockeyService
  ) {}

  ngOnInit() {
    const id = this.dynamicDialog.data.id
    this.type = this.dynamicDialog.data.type

    if (this.type == 'jockey') {
      this.fetchJockeyInfos(id)
    } else {
      this.fetchHorseInfos(id)
    }

  }

  fetchJockeyInfos(id: number) {
    this.jockeyService.getJockeyById(id)
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.jockey = value.jockey
          if (this.jockey.jockeyResults) {
            this.sortResults(this.jockey.jockeyResults)
            this.calculateLastResultsAverage(this.jockey.jockeyResults)
          }
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
          if(this.horse.horseResults) {
            this.sortResults(this.horse.horseResults)
            this.calculateLastResultsAverage(this.horse.horseResults)
          }
          this.dynamicDialog.header = "Cavalo: " + value.horse.name
        }
      })
  }

  sortResults(results: Results[]) {
    results.sort((a, b) => {
      return new Date(b.resultDate).getTime() - new Date(a.resultDate).getTime()
    })
  }

  calculateLastResultsAverage(result: Results[]) {
    let count = 0
    let sum = 0

    for (let i = 0; i < result.length && i < 5; i++) {
      let resultString = result[i].result.split('/')[0]
      if (Number.isInteger(+resultString)) {
        sum += +resultString
        count++
      }
    }

    if (count == 0) {
      this.lastResultsAverage = null
    } else {
      this.lastResultsAverage = Math.round(sum / count)
    }

  }
}
