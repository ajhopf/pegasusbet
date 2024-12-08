import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";

import { AdminHomeComponent } from './page/admin-home/admin-home.component';

import { ADMIN_ROUTES } from "./admin.routing";
import { CardModule } from "primeng/card";
import { TableModule } from "primeng/table";
import { ButtonModule } from "primeng/button";
import { TooltipModule } from "primeng/tooltip";
import { SharedModule } from "../shared/shared.module";
import { HorsesDisplayComponent } from './components/horses-display/horses-display.component';
import { JockeysDisplayComponent } from './components/jockeys-display/jockeys-display.component';
import { InputTextModule } from "primeng/inputtext";
import { PaginatorModule } from "primeng/paginator";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ConfirmationService } from "primeng/api";
import { RippleModule } from "primeng/ripple";


@NgModule({
  declarations: [
    AdminHomeComponent,
    HorsesDisplayComponent,
    JockeysDisplayComponent
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
    ConfirmDialogModule
  ],
  providers: [ConfirmationService]
})
export class AdminModule { }
