import { Routes } from "@angular/router";

import { RacesPageComponent } from "./components/races/races-page/races-page.component";

import { HorseFormComponent } from "./components/horses/horse-form/horse-form.component";

import { UsersPageComponent } from "./page/users-page/users-page.component";
import { HorsesTableComponent } from "./components/horses/horses-table/horses-table.component";
import { JockeysTableComponent } from "./components/jockeys/jockeys-table.component";

export const USERS_ROUTES: Routes = [
  {
    path: '',
    component: UsersPageComponent,
    children: [
      {
        path: '',
        component: RacesPageComponent
      },
      {
        path: 'horses',
        component: HorsesTableComponent,
      },
      {
        path: 'horses/form',
        component: HorseFormComponent,
      },
      {
        path: 'jockeys',
        component: JockeysTableComponent,
      },
    ]
  },
];
