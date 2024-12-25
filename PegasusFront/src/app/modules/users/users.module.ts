import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RacesPageComponent } from './components/races/races-page/races-page.component';
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { RaceCardComponent } from "./components/races/race-card/race-card.component";
import { PanelModule } from "primeng/panel";
import { CardModule } from "primeng/card";
import { InformationOverlayComponent } from './components/information-overlay/information-overlay.component';
import { DialogService } from "primeng/dynamicdialog";
import { SidebarModule } from "primeng/sidebar";
import { SidebarContentComponent } from './components/sidebar-content/sidebar-content.component';
import { UsersPageComponent } from './page/users-page/users-page.component';
import { USERS_ROUTES } from "./users.routing";
import { ConfirmationService } from "primeng/api";
import { PaginatorModule } from "primeng/paginator";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ReactiveFormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { KeyFilterModule } from "primeng/keyfilter";
import { ChipModule } from "primeng/chip";
import { ChipsModule } from "primeng/chips";
import { CalendarModule } from "primeng/calendar";
import { RippleModule } from "primeng/ripple";
import { JockeysTableComponent } from "./components/jockeys/jockeys-table.component";
import { HorsesTableComponent } from "./components/horses/horses-table/horses-table.component";
import { BetsTableComponent } from './components/bets/bets-table/bets-table.component';


@NgModule({
  declarations: [
    RaceCardComponent,
    RacesPageComponent,
    InformationOverlayComponent,
    SidebarContentComponent,
    UsersPageComponent,
    JockeysTableComponent,
    HorsesTableComponent,
    BetsTableComponent
  ],
  imports: [
    CommonModule,
    PanelModule,
    CardModule,
    RouterModule.forChild(USERS_ROUTES),
    SharedModule,
    SidebarModule,
    PaginatorModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
    InputTextModule,
    KeyFilterModule,
    ChipModule,
    ChipsModule,
    CalendarModule,
    RippleModule
  ],
  providers: [DialogService, ConfirmationService]
})
export class UsersModule { }
