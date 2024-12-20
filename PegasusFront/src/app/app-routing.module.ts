import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from "./guards/auth.guard";
import { AdminGuard } from "./guards/admin.guard";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }, {
    path: 'admin',
    loadChildren: () =>
      import('./modules/admin/admin.module').then(
        (m) => m.AdminModule
      ),
    canActivate: [AdminGuard]
  }, {
    path: 'races',
    loadChildren: () =>
      import('./modules/races/races.module').then(
        (m) => m.RacesModule
      ),
    canActivate: [AuthGuard]
  }, {
    path: 'login',
    loadChildren: () =>
    import('./modules/login/login.module').then(
      (m) => m.LoginModule
    )
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
