import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RacesPageComponent } from './page/races-page/races-page.component';
import { RouterModule } from "@angular/router";
import { RACES_ROUTES } from "./races.routing";
import { SharedModule } from "../shared/shared.module";
import { RacesCardsComponent } from "./components/races-cards/races-cards.component";
import { PanelModule } from "primeng/panel";
import { CardModule } from "primeng/card";


@NgModule({
  declarations: [
    RacesCardsComponent,
    RacesPageComponent
  ],
  imports: [
    CommonModule,
    PanelModule,
    CardModule,
    RouterModule.forChild(RACES_ROUTES),
    SharedModule,
  ]
})
export class RacesModule { }
