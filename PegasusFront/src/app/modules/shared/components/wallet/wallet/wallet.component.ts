import { Component, OnDestroy, OnInit } from '@angular/core'
import { WalletService } from "../../../../../services/wallet/wallet.service";
import { Subject, take, takeUntil } from 'rxjs'
import { TransactionRequest } from '../../../../../models/wallet/TransactionRequest'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit, OnDestroy {
  $destroy: Subject<void> = new Subject<void>();
  userMoney = 0

  amountInput: number = 0

  constructor(
    private walletService: WalletService,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.walletService.userMoney$
      .pipe(takeUntil(this.$destroy))
      .subscribe((money) => this.userMoney = money)

    this.walletService.fetchUserWalletInfo()
      .pipe(take(1))
      .subscribe({
        next: walletResponse => {
          this.userMoney = walletResponse.wallet.amount
        },
        error: err => {
          console.log(err)
        }
      })
  }

  handleNewTransaction(type: 'DEPOSIT' | 'WITHDRAWAL'): void {
    const amount = this.amountInput;

    if (amount <= 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'O valor deve ser diferente de 0',
      })

      return
    }

    const transactionRequest: TransactionRequest = {
      amount,
      transactionType: type
    }

    this.walletService.addTransaction(transactionRequest)
      .pipe(take(1))
      .subscribe({
        next: walletResponse => {
          this.walletService.updateUserMoney(walletResponse.wallet.amount)

          this.messageService.add({
            severity: 'success',
            summary: 'Operação realizada com sucesso',
          })

          this.amountInput = 0
        },
        error: err => {
          console.log(err)

          this.messageService.add({
            severity: 'error',
            summary: 'Erro ao realizar operação',
            detail: err.error.message
          })

          this.amountInput = 0
        }
      })
  }

  ngOnDestroy(): void {
    this.$destroy.next()
    this.$destroy.complete()
  }
}
