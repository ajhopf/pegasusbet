import { AdminHomeComponent } from "./page/admin-home/admin-home.component";
import { Routes } from "@angular/router";
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
        path: 'new-race',
        component: RaceFormComponent
      }
    ]
  }
];
