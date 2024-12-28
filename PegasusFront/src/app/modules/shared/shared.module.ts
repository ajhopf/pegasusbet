import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardModule } from "primeng/card";
import { SharedModule as SharedModuleNg } from "primeng/api";
import { TableModule } from "primeng/table";

import { TableComponent } from './components/table/table.component';
import { HeaderMenuBarComponent } from './components/header-menu-bar/header-menu-bar.component';
import { MenubarModule } from "primeng/menubar";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule } from "@angular/forms";
import { WalletComponent } from './components/wallet/wallet/wallet.component';
import { DialogService } from "primeng/dynamicdialog";
import { InputNumberModule } from 'primeng/inputnumber';
import { HorseJockeyInfoComponent } from './components/horse-jockey-info/horse-jockey-info.component'
import { NotificationComponent } from "./components/notification/notification/notification.component";
import { BadgeModule } from "primeng/badge";


@NgModule({
  declarations: [
    NotificationComponent,
    TableComponent,
    HeaderMenuBarComponent,
    WalletComponent,
    HorseJockeyInfoComponent
  ],
  imports: [
    CommonModule,
    CardModule,
    SharedModuleNg,
    TableModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    InputNumberModule,
    BadgeModule
  ],
  providers: [
    DialogService
  ],
  exports: [
    TableComponent,
    HeaderMenuBarComponent,
    HorseJockeyInfoComponent
  ]
})
export class SharedModule { }
