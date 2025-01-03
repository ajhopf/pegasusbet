import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { RaceHorseJockey } from "../../../../models/races/RaceHorseJockey";
import { BetService } from '../../../../services/bet/bet.service'
import { CreateBetRequest } from '../../../../models/bet/CreateBetRequest'
import { take } from 'rxjs'
import { MessageService } from 'primeng/api'
import { WalletService } from '../../../../services/wallet/wallet.service'

@Component({
  selector: 'app-place-bet-sidebar',
  templateUrl: './place-bet-sidebar.component.html',
  styleUrls: ['./place-bet-sidebar.component.css']
})
export class PlaceBetSidebarComponent implements OnInit {
  @Input() horseJockey: RaceHorseJockey | undefined
  @Input() participants: number = 0
  @Output() betCreated = new EventEmitter<string>();

  amountInput = 0
  selectedBetType: 'WIN' | 'PLACE' | 'SHOW' = 'WIN'

  betTypes: any[] = [
    { label: 'Win', value: 'WIN' },
  ];

  constructor(
    private betService: BetService,
    private messageService: MessageService,
    private walletService: WalletService,
    ) {
  }

  ngOnInit(): void {
    if (this.participants > 5) {
      this.betTypes.push({label: 'Place', value: 'PLACE'})
    }

    if (this.participants > 9) {
      this.betTypes.push({label: 'Show', value: 'SHOW'})
    }
  }

  calculateReturn() {
    const amount = this.amountInput;
    const selectedBetType = this.selectedBetType;


    if (this.horseJockey) {
      const totalReturn = amount * this.horseJockey.odds.currentOdd
      const totalEarning = totalReturn - amount

      switch (selectedBetType) {
        case 'WIN':
          return totalReturn
        case 'PLACE':
          return totalReturn - ( totalEarning * 0.3 )
        default:
          return totalReturn - ( totalEarning * 0.5 )
      }
    } else {
      return 0
    }

  }

  createBet() {
    if (this.amountInput > 0 && this.horseJockey) {
      const createBetRequest: CreateBetRequest = {
        amount: this.amountInput,
        betType: this.selectedBetType,
        raceHorseJockeyId: this.horseJockey.id
      }

      this.betService.createBet(createBetRequest)
        .pipe(take(1))
        .subscribe({
          next: carresponse => {
            this.messageService.add({
              severity: 'success',
              summary: 'Aposta criada com sucesso'
            })

            this.betCreated.emit('created')

            this.walletService.fetchUserWalletInfo()
              .subscribe({
                next: response => {
                  this.walletService.updateUserMoney(response.wallet.amount)
                },
                error: error => {
                  console.log(error)
                }
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
