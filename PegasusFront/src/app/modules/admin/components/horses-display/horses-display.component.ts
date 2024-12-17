import { Component, OnDestroy, OnInit } from '@angular/core';
import { Horse } from "../../../../models/horse/Horse";
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";
import { HorseService } from "../../../../services/horse/horse.service";
import { Subject, take, takeUntil } from "rxjs";
import { ConfirmationService, MessageService } from "primeng/api";
import { Router } from "@angular/router";

@Component({
  selector: 'app-horses-display',
  templateUrl: './horses-display.component.html'
})
export class HorsesDisplayComponent implements OnInit, OnDestroy{
  private destroy$= new Subject<void>()

  horses: Horse[] = []
  filteredHorses: Horse[] = []
  totalRecords: number = 0
  // filter: string = ''
  loading: boolean = false

  columns: TableColumnDefinition[] = [
    {header: 'id', field: 'id'},
    {header: 'Nome', field: 'name'},
    {header: 'Idade', field: 'age'},
    {header: 'Sexo', field: 'sex'},
    {header: 'Últimos Resultados', field: 'lastResults'},
    {header: 'Corridas', field: 'numberOfRaces'},
    {header: 'Vitórias', field: 'numberOfVictories'},
  ]

  constructor(
    private horseService: HorseService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private router: Router
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

  handleAddHorse() {
    return this.router.navigate(['/admin/horses/form'])
  }

  handleDeleteHorse(horseId: string) {
    this.confirmationService.confirm({
      message: `Confirma a exclusão do cavalo?`,
      header: 'Confirmação de exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      accept: () => this.deleteHorse(horseId),
    });
  }

  deleteHorse(horseId: string) {
    this.horseService.deleteHorse(horseId)
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          if (response && response.status == 204) {
            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: 'Cavalo removido com sucesso!',
              life: 2500,
            })
          }

          this.horses = this.horses.filter(horse=> horse.id != +horseId)
        },
        error: (err) => {
          console.error('Erro ao deletar cavalo:', err);
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Não foi possível remover o cavalo.',
            life: 2500,
          });
        }
      })
  }

  handleEditHorse(horseId: string) {
    console.log(horseId)

    this.messageService.add({
      severity: 'error',
      summary: 'Erro',
      detail: 'Erro ao remover produto!',
      life: 2500,
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }

}
