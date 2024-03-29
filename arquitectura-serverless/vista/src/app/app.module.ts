import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './security/login/login.component';
import {ExistingPasswordComponent} from './security/password/existing/existing-password.component';
import {TemporaryPasswordComponent} from './security/password/temporary/temporary-password.component';
import {ForgotPasswordStep1Component, ForgotPasswordStep2Component} from './security/forgot/forgotPassword.component';
import {MenuComponent} from './menu/menu.component';
import {AppMaterialModule} from './app.material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpRequest} from '@angular/common/http';
import {ControlMessagesComponent} from './shared/control-messages/control-messages.component';
import {SecurityService} from './security/security.service';
import {InactivityService} from './shared/inactivity/inactivity.service';
import {NonAuthGuardService} from './shared/guards/nonauth-guard.service';
import {AuthGuardService} from './shared/guards/auth-guard.service';
import {AppHttpInterceptor} from './security/interceptors/app-http-interceptor';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BuscarServicioComponent} from './buscar-servicio/buscar-servicio.component';
import {HistoricoComponent} from './historico/historico.component';
import {ConsultaPagoComponent} from './consulta-pago/consulta-pago.component';
import {ConfirmacionPagoDialogComponent} from './confirmacion-pago/confirmacion-pago';
import {BlockUIHttpModule} from 'ng-block-ui/http';
import {BlockUIModule} from 'ng-block-ui';
import {FavoritoComponent} from './favorito/favorito.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    HomeComponent,
    LoginComponent,
    ExistingPasswordComponent,
    TemporaryPasswordComponent,
    ForgotPasswordStep1Component,
    ForgotPasswordStep2Component,
    MenuComponent,
    ControlMessagesComponent,
    BuscarServicioComponent,
    HistoricoComponent,
    ConsultaPagoComponent,
    ConfirmacionPagoDialogComponent,
    FavoritoComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    AppMaterialModule, // material
    ReactiveFormsModule,
    FormsModule,
    SimpleNotificationsModule,
    HttpClientModule,
    SimpleNotificationsModule.forRoot(),
    BlockUIModule.forRoot({
      message: 'Cargando...',
      delayStart: 150,
      delayStop: 150
    }),
    BlockUIHttpModule.forRoot(),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptor,
      multi: true
    },
    AuthGuardService,
    NonAuthGuardService,
    SecurityService,
    InactivityService,
  ],
  entryComponents: [ConfirmacionPagoDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
