import { Component, Input } from '@angular/core';
import { MenuItem } from "primeng/api";
import { AuthService } from "../../../../services/auth/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-header-menu-bar',
  templateUrl: './header-menu-bar.component.html',
  styleUrls: ['./header-menu-bar.component.css']
})
export class HeaderMenuBarComponent {
  @Input() menuItems: MenuItem[] = []

  constructor(private authService: AuthService, private router: Router) {
  }

  handleLogout(){
    this.authService.logout()
    this.router.navigate(['/'])
  }
}
