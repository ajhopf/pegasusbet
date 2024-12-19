import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RacesPageComponent } from './page/races-page/races-page.component';
import { RouterModule } from "@angular/router";
import { RACES_ROUTES } from "./races.routing";
import { SharedModule } from "../shared/shared.module";
import { RaceCardComponent } from "./components/race-card/race-card.component";
import { PanelModule } from "primeng/panel";
import { CardModule } from "primeng/card";
import { InformationOverlayComponent } from './components/information-overlay/information-overlay.component';
import { DialogService } from "primeng/dynamicdialog";
import { SidebarModule } from "primeng/sidebar";
import { SidebarContentComponent } from './components/sidebar-content/sidebar-content.component';


@NgModule({
  declarations: [
    RaceCardComponent,
    RacesPageComponent,
    InformationOverlayComponent,
    SidebarContentComponent
  ],
  imports: [
    CommonModule,
    PanelModule,
    CardModule,
    RouterModule.forChild(RACES_ROUTES),
    SharedModule,
    SidebarModule
  ],
  providers: [DialogService]
})
export class RacesModule { }
