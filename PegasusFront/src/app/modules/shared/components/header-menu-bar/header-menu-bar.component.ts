import { Component, Input } from '@angular/core';
import { MenuItem } from "primeng/api";
import { AuthService } from "../../../../services/auth/auth.service";
import { Router } from "@angular/router";
import { DialogService, DynamicDialogComponent, DynamicDialogRef } from "primeng/dynamicdialog";
import { WalletComponent } from "../wallet/wallet/wallet.component";

@Component({
  selector: 'app-header-menu-bar',
  templateUrl: './header-menu-bar.component.html',
  styleUrls: ['./header-menu-bar.component.css']
})
export class HeaderMenuBarComponent {
  @Input() menuItems: MenuItem[] = []

  ref: DynamicDialogRef | undefined;

  constructor(
    private authService: AuthService,
    private router: Router,
    public dialogService: DialogService
    ) {
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
}
