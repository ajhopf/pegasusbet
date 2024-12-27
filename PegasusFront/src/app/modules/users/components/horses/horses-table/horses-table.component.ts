import { Component, OnDestroy, OnInit } from '@angular/core';
import { Horse } from "../../../../../models/horse/Horse";
import { TableColumnDefinition } from "../../../../../models/table/TableColumnDefinition";
import { HorseService } from "../../../../../services/horse/horse.service";
import { Subject, take, takeUntil } from "rxjs";
import { ConfirmationService, MessageService } from "primeng/api";
import { Router } from "@angular/router";
import { InformationOverlayComponent } from '../../../../shared/components/information-overlay/information-overlay.component'
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog'

@Component({
  selector: 'app-horses-table',
  styleUrls: ['./horses-table.componente.css'],
  templateUrl: './horses-table.component.html'
})
export class HorsesTableComponent implements OnInit, OnDestroy{
  private destroy$= new Subject<void>()
  ref: DynamicDialogRef | undefined;

  horses: Horse[] = []
  filteredHorses: Horse[] = []
  totalRecords: number = 0
  loading: boolean = false

  columns: TableColumnDefinition[] = [
    {header: 'Nome', field: 'name'},
    {header: 'Idade', field: 'age'},
    {header: 'Sexo', field: 'sex'},
    {header: 'Corridas', field: 'numberOfRaces'},
    {header: 'Vitórias', field: 'numberOfVictories'},
  ]

  constructor(
    private horseService: HorseService,
    private dialogService: DialogService,
    ) {}

  ngOnInit(): void {
    this.fetchHorses()
  }

  fetchHorses(): void {
    this.loading = true

    this.horseService.fetchHorses()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          if (response) {
            this.horses = response.horses
            this.filteredHorses = response.horses
            this.totalRecords = response.items
            this.loading = false
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  handleFilterHorses(filterString: string) {
    this.filteredHorses = this.horses.filter(horse => horse.name.toUpperCase().includes(filterString.toUpperCase()))
  }

  showHorseInfo(horse: Horse) {
    const isSmallScreen = window.innerWidth < 768;
    const width = isSmallScreen ? '90%' : '50%';

    this.ref = this.dialogService.open(InformationOverlayComponent, {
        data: { id: horse.id, type: 'horse' },
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
