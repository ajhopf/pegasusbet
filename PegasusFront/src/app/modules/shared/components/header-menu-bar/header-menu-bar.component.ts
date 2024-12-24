import { Component, Input, OnChanges, OnDestroy, OnInit } from '@angular/core'
import { MenuItem } from "primeng/api";
import { AuthService } from "../../../../services/auth/auth.service";
import { Router } from "@angular/router";
import { DialogService, DynamicDialogComponent, DynamicDialogRef } from "primeng/dynamicdialog";
import { WalletComponent } from "../wallet/wallet/wallet.component";
import { WalletService } from '../../../../services/wallet/wallet.service'
import { Subject, take, takeUntil } from 'rxjs'

@Component({
  selector: 'app-header-menu-bar',
  templateUrl: './header-menu-bar.component.html',
  styleUrls: ['./header-menu-bar.component.css']
})
export class HeaderMenuBarComponent implements OnInit, OnDestroy {
  $destroy: Subject<void> = new Subject<void>();
  @Input() menuItems: MenuItem[] = []
  userMoney = 0

  ref: DynamicDialogRef | undefined;

  constructor(
    private authService: AuthService,
    private router: Router,
    private walletService: WalletService,
    public dialogService: DialogService
    ) {
  }

  ngOnInit() {
    this.getWalletInfo()
  }

  handleWalletClick() {
    this.ref = this.dialogService.open(WalletComponent, {
      width: '90%',
      contentStyle: { overflow: 'auto' },
      baseZIndex: 10000
    })
  }

  handleLogout(){
    this.authService.logout()
    this.router.navigate(['/'])
  }

  getWalletInfo() {
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

  ngOnDestroy(): void {
    this.$destroy.next()
    this.$destroy.complete()
  }
}
