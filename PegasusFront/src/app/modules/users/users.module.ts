import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RacesPageComponent } from './components/races/races-page/races-page.component';
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { RaceCardComponent } from "../shared/components/race-card/race-card.component";
import { PanelModule } from "primeng/panel";
import { CardModule } from "primeng/card";
import { InformationOverlayComponent } from '../shared/components/information-overlay/information-overlay.component';
import { DialogService } from "primeng/dynamicdialog";
import { SidebarModule } from "primeng/sidebar";
import { PlaceBetSidebarComponent } from './components/place-bet-sidebar/place-bet-sidebar.component';
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
import { StartRaceSimulationComponent } from '../admin/components/races/start-race-simulation/race-simulation/start-race-simulation.component';
import { LiveComponent } from './components/races/live/live/live.component';
import { ProgressBarModule } from 'primeng/progressbar'
import { ToastModule } from 'primeng/toast'
import { ProgressSpinnerModule } from 'primeng/progressspinner'
import { TooltipModule } from 'primeng/tooltip'
import { SelectButtonModule } from 'primeng/selectbutton'


@NgModule({
  declarations: [
    RaceCardComponent,
    RacesPageComponent,
    InformationOverlayComponent,
    PlaceBetSidebarComponent,
    UsersPageComponent,
    JockeysTableComponent,
    HorsesTableComponent,
    BetsTableComponent,
    StartRaceSimulationComponent,
    LiveComponent
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
        RippleModule,
        ProgressBarModule,
        ToastModule,
        ProgressSpinnerModule,
        TooltipModule,
        SelectButtonModule
    ],
  providers: [DialogService, ConfirmationService]
})
export class UsersModule { }
