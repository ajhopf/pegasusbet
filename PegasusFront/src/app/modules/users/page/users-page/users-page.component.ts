import { Component } from '@angular/core';
import { MenuItem } from "primeng/api";

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent {

  menuItems: MenuItem[] = [
    {label: 'Corridas', icon: 'fa fa-flag-checkered', routerLink: '/races'},
    {label: 'Cavalos', icon: 'fa fa-horse', routerLink:'/races/horses'},
    {label: 'Jockeys', icon: 'fa fa-hat-cowboy', routerLink:'/races/jockeys'},
    {label: 'Apostas', icon: 'fa fa-hat-wizard', routerLink:'/races/bets'},
    {label: 'Live', icon: 'fa fa-tower-broadcast', routerLink:'/races/live'},
  ]

}
