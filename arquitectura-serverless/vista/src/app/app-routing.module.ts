import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './security/login/login.component';
import {TemporaryPasswordComponent} from './security/password/temporary/temporary-password.component';
import {ForgotPasswordStep1Component, ForgotPasswordStep2Component} from './security/forgot/forgotPassword.component';
import {ExistingPasswordComponent} from './security/password/existing/existing-password.component';
import {NonAuthGuardService} from './shared/guards/nonauth-guard.service';
import {AuthGuardService} from './shared/guards/auth-guard.service';
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {BuscarServicioComponent} from './buscar-servicio/buscar-servicio.component';
import {HistoricoComponent} from './historico/historico.component';
import {ConsultaPagoComponent} from './consulta-pago/consulta-pago.component';

const routes: Routes = [

  {path: 'security/login', component: LoginComponent, canActivate: [NonAuthGuardService]},
  {path: 'security/temporaryPassword/:param', component: TemporaryPasswordComponent, canActivate: [NonAuthGuardService]},
  {path: 'security/forgotPassword/:param', component: ForgotPasswordStep2Component, canActivate: [NonAuthGuardService]},
  {path: 'security/forgotPassword', component: ForgotPasswordStep1Component, canActivate: [NonAuthGuardService]},

  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuardService]},
  {path: 'existingPassword', component: ExistingPasswordComponent, canActivate: [AuthGuardService]},

  // app
  {path: 'buscar-servicio', component: BuscarServicioComponent, canActivate: [AuthGuardService]},
  {path: 'consulta-pago/:servicio', component: ConsultaPagoComponent, canActivate: [AuthGuardService]},
  {path: 'historico', component: HistoricoComponent, canActivate: [AuthGuardService]},

  {path: '404', component: NotFoundComponent, pathMatch: 'full', canActivate: [AuthGuardService]}, // not found manually
  {path: '**', redirectTo: '/404', pathMatch: 'full'} // not found

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
