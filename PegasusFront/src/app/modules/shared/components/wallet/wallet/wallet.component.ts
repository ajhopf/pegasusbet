import { Component, OnInit } from '@angular/core';
import { WalletService } from "../../../../../services/wallet/wallet.service";
import { take } from "rxjs";
import { WalletResponse } from "../../../../../models/wallet/WalletResponse";
import { Wallet } from "../../../../../models/wallet/Wallet";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit{
  wallet: Wallet | undefined

  constructor(private walletService: WalletService) {
  }

  ngOnInit() {
    this.walletService.fetchUserWalletInfo()
      .pipe(take(1))
      .subscribe({
        next: walletResponse => {
          this.wallet = walletResponse.wallet
        },
        error: err => {
          console.log(err)
        }
      })
  }
}
