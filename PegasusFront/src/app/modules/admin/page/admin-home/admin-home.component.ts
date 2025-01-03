import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from "rxjs";
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";
import { JockeyService } from "../../../../services/jockey/jockey.service";
import { Jockey } from "../../../../models/jockey/Jockey";
import { MenuItem } from "primeng/api";

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html'
})

export class AdminHomeComponent {

  menuItems: MenuItem[] = [
    {label: 'Home', icon: 'pi pi-home', routerLink: '/admin'},
    {label: 'Criar Corrida', icon: 'fa fa-flag-checkered', routerLink: '/admin/race'},
    {label: 'Criar Circuito', icon: 'fa-brands fa-squarespace', routerLink: '/admin/racecourse'},
    {label: 'Iniciar Corrida', icon: 'fa-brands fa-squarespace', routerLink: '/admin/start-race'},
    {label: 'Área de Usuário', icon: 'fa-solid fa-users', routerLink: '/races'}
  ]

}
