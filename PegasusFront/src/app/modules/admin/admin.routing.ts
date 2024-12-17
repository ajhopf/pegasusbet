import { AdminHomeComponent } from "./page/admin-home/admin-home.component";
import { Routes } from "@angular/router";
import { HorsesDisplayComponent } from "./components/horses-display/horses-display.component";
import { JockeysDisplayComponent } from "./components/jockeys-display/jockeys-display.component";
import { HorseFormComponent } from "./components/horse-form/horse-form.component";

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
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
      }
    ]
  }
];
