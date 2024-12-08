import { Component, OnDestroy, OnInit } from '@angular/core';
import { Jockey } from "../../../../models/jockey/Jockey";
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";
import { Subject, takeUntil } from "rxjs";
import { JockeyService } from "../../../../services/jockey/jockey.service";

@Component({
  selector: 'app-jockeys-display',
  templateUrl: './jockeys-display.component.html'
})
export class JockeysDisplayComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>()

  jockeys: Jockey[] = []

  jockeyColumns: TableColumnDefinition[] = [
    {header: 'id', field: 'id'},
    {header: 'name', field: 'name'},
    {header: 'lastResults', field: 'lastResults'},
    {header: 'numberOfRaces', field: 'numberOfRaces'},
    {header: 'numberOfVictories', field: 'numberOfVictories'},
  ]

  constructor(private jockeyService: JockeyService) {
  }

  ngOnInit(): void {
    this.fetchJockeys()
  }

  fetchJockeys(): void {
    this.jockeyService.fetchJockeys(0, 1000)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          if (response) {
            this.jockeys = response.jockeys
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }


}
