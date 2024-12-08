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
    {label: 'Home', icon: 'pi pi-home', routerLink: '/'},
    {label: 'Horses', icon: 'fa fa-horse-head', routerLink: 'horses'},
    {label: 'Jockeys', icon: 'fa fa-hat-cowboy', routerLink: 'jockeys'},
  ]

}
