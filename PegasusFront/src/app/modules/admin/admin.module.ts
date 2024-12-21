import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from "@angular/router";

import { AdminHomeComponent } from './page/admin-home/admin-home.component';

import { ADMIN_ROUTES } from "./admin.routing";
import { CardModule } from "primeng/card";
import { TableModule } from "primeng/table";
import { ButtonModule } from "primeng/button";
import { TooltipModule } from "primeng/tooltip";
import { SharedModule } from "../shared/shared.module";
import { HorsesDisplayComponent } from './components/horses/horses-display/horses-display.component';
import { JockeysDisplayComponent } from './components/jockeys-display/jockeys-display.component';
import { InputTextModule } from "primeng/inputtext";
import { PaginatorModule } from "primeng/paginator";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ConfirmationService } from "primeng/api";
import { RippleModule } from "primeng/ripple";
import { ReactiveFormsModule } from "@angular/forms";
import { HorseFormComponent } from './components/horses/horse-form/horse-form.component';
import { KeyFilterModule } from "primeng/keyfilter";
import { ChipModule } from "primeng/chip";
import { ChipsModule } from "primeng/chips";
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RaceFormComponent } from './components/races/race-form/race-form.component';
import { CalendarModule } from "primeng/calendar";


@NgModule({
  declarations: [
    AdminHomeComponent,
    HorsesDisplayComponent,
    JockeysDisplayComponent,
    HorseFormComponent,
    DashboardComponent,
    RaceFormComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(ADMIN_ROUTES),
    CardModule,
    TableModule,
    ButtonModule,
    TooltipModule,
    SharedModule,
    InputTextModule,
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
  providers: [ConfirmationService, DatePipe]
})
export class AdminModule { }
