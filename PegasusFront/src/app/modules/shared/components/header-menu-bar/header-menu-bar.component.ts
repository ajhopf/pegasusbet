import { Component, Input } from '@angular/core';
import { MenuItem } from "primeng/api";

@Component({
  selector: 'app-header-menu-bar',
  templateUrl: './header-menu-bar.component.html',
  styleUrls: ['./header-menu-bar.component.css']
})
export class HeaderMenuBarComponent {
  @Input() menuItems: MenuItem[] = []
}
