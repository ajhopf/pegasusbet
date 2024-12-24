import { Component, Input } from '@angular/core';
import { RaceHorseJockey } from "../../../../models/races/RaceHorseJockey";
import { BetService } from '../../../../services/bet/bet.service'
import { CreateBetRequest } from '../../../../models/bet/CreateBetRequest'
import { take } from 'rxjs'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-sidebar-content',
  templateUrl: './sidebar-content.component.html',
  styleUrls: ['./sidebar-content.component.css']
})
export class SidebarContentComponent {
  @Input() horseJockey: RaceHorseJockey | undefined

  amountInput = 0

  constructor(
    private betService: BetService,
    private messageService: MessageService,

    ) {
  }

  createBet() {
    if (this.amountInput > 0 && this.horseJockey) {
      const createBetRequest: CreateBetRequest = {
        amount: this.amountInput,
        raceHorseJockeyId: this.horseJockey.id
      }

      this.betService.createBet(createBetRequest)
        .pipe(take(1))
        .subscribe({
          next: response => {
            this.messageService.add({
              severity: 'success',
              summary: 'Aposta criada com sucesso'
            })
          },
          error: error => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erro ao criar aposta',
              detail: error.message
            })
          }
        })
    }


  }
}
