import { AdminHomeComponent } from "./page/admin-home/admin-home.component";
import { Routes } from "@angular/router";
import { HorsesDisplayComponent } from "./components/horses/horses-display/horses-display.component";
import { JockeysDisplayComponent } from "./components/jockeys-display/jockeys-display.component";
import { HorseFormComponent } from "./components/horses/horse-form/horse-form.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { RaceFormComponent } from "./components/races/race-form/race-form.component";

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path: 'horses',
        component: HorsesDisplayComponent,
      },
      {
        path: 'horses/form',
        component: HorseFormComponent,
      },
      {
        path: 'jockeys',
        component: JockeysDisplayComponent,
      },
      {
        path: 'races',
        component: RaceFormComponent
      }
    ]
  }
];
