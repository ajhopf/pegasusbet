import { Component, OnDestroy, OnInit } from '@angular/core';
import { Jockey } from "../../../../models/jockey/Jockey";
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";
import { Subject, takeUntil } from "rxjs";
import { JockeyService } from "../../../../services/jockey/jockey.service";
import { Horse } from '../../../../models/horse/Horse'
import { InformationOverlayComponent } from '../../../shared/components/information-overlay/information-overlay.component'
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog'

@Component({
  selector: 'app-jockeys-table',
  styleUrls: ['./jockeys-table.component.css'],
  templateUrl: './jockeys-table.component.html'
})
export class JockeysTableComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>()
  ref: DynamicDialogRef | undefined;

  jockeys: Jockey[] = []
  filteredJockeys: Jockey[] = []

  jockeyColumns: TableColumnDefinition[] = [
    {header: 'Nome', field: 'name'},
    {header: 'Número de Corridas', field: 'numberOfRaces'},
    {header: 'Número de Vitórias', field: 'numberOfVictories'},
  ]

  constructor(
    private jockeyService: JockeyService,
    private dialogService: DialogService
    ) {
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
            this.filteredJockeys = response.jockeys
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  handleFilterJockeys(filterString: string) {
    this.filteredJockeys = this.jockeys.filter(jockey => jockey.name.toUpperCase().includes(filterString.toUpperCase()))
  }

  showJockeyInfo(jockey: Jockey) {
    const isSmallScreen = window.innerWidth < 768;
    const width = isSmallScreen ? '90%' : '50%';

    this.ref = this.dialogService.open(InformationOverlayComponent, {
        data: { id: jockey.id, type: 'jockey' },
        width: width,
        contentStyle: { overflow: 'auto' },
        baseZIndex: 10000
      }
    );
  }


  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }


}
