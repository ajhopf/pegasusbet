import { Component } from '@angular/core';
import { MenuItem } from "primeng/api";

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent {

  menuItems: MenuItem[] = [
    {label: 'Corridas', icon: 'pi pi-home', routerLink: '/races'},
    {label: 'Cavalos', icon: 'fa fa-horse', routerLink:'/races/horses'},
    {label: 'Jockeys', icon: 'fa fa-hat-cowboy', routerLink:'/races/jockeys'},
  ]

}
