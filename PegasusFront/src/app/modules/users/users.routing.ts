import { Routes } from "@angular/router";

import { RacesPageComponent } from "./components/races/races-page/races-page.component";
import { UsersPageComponent } from "./page/users-page/users-page.component";
import { HorsesTableComponent } from "./components/horses/horses-table/horses-table.component";
import { JockeysTableComponent } from "./components/jockeys/jockeys-table.component";
import { BetsTableComponent } from './components/bets/bets-table/bets-table.component'
import { StartRaceSimulationComponent } from '../admin/components/races/start-race-simulation/race-simulation/start-race-simulation.component'
import { LiveComponent } from './components/races/live/live/live.component'

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
        path: 'jockeys',
        component: JockeysTableComponent,
      },
      {
        path: 'bets',
        component: BetsTableComponent,
      },
      {
        path: 'live',
        component: LiveComponent
      }
    ]
  },
];
