import { AdminHomeComponent } from "./page/admin-home/admin-home.component";
import { Routes } from "@angular/router";
import { HorsesDisplayComponent } from "./components/horses-display/horses-display.component";
import { JockeysDisplayComponent } from "./components/jockeys-display/jockeys-display.component";

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminHomeComponent,
    children: [
      {
        path: 'horses', // child route path
        component: HorsesDisplayComponent, // child route component that the router renders
      },
      {
        path: 'jockeys', // child route path
        component: JockeysDisplayComponent, // child route component that the router renders
      }
    ]
  }
];
