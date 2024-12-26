import { AdminHomeComponent } from "./page/admin-home/admin-home.component";
import { Routes } from "@angular/router";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { RaceFormComponent } from "./components/races/race-form/race-form.component";
import { RacecourseFormComponent } from './components/races/racecourse-form/racecourse-form.component'
import { StartRaceSimulationComponent } from './components/races/start-race-simulation/race-simulation/start-race-simulation.component'

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
        path: 'race',
        component: RaceFormComponent
      },
      {
        path: 'racecourse',
        component: RacecourseFormComponent
      },
      {
        path: 'start-race',
        component: StartRaceSimulationComponent
      }
    ]
  }
];
