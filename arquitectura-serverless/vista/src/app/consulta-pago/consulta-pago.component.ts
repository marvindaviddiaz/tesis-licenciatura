import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ConsultaPagoService} from './consulta-pago.service';
import {HttpErrorResponse} from '@angular/common/http';
import {NotificationsService} from 'angular2-notifications';
import {HttpUtilService} from '../shared/util/http-util.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Identificador} from '../dominio/Identificador';
import {CuentaService} from '../cuenta/cuenta.service';
import {Cuenta} from '../dominio/Cuenta';
import {MatDialog} from '@angular/material';
import {ServicioService} from '../servicio/servicio.service';
import {FavoritoService} from '../favorito/favorito.service';
import {Favorito} from '../dominio/Favorito';

@Component({
  selector: 'app-consulta-pago',
  templateUrl: './consulta-pago.component.html',
  styleUrls: ['./consulta-pago.component.css']
})
export class ConsultaPagoComponent implements OnInit, OnDestroy {

  form: FormGroup;
  formPay: FormGroup;
  identificadores: Identificador[] = [];
  subscriptionRoute: Subscription;
  servicio: number;
  title: string;
  saldo: number;
  cuentas: Cuenta[];
  cuenta: Cuenta;
  paso: number;
  idPago: number;
  identificadoresUsados = {};
  favorito: number;

  constructor(private service: ConsultaPagoService,
              private servicioService: ServicioService,
              private cuentaService: CuentaService,
              private favoritoService: FavoritoService,
              private route: ActivatedRoute,
              private notifications: NotificationsService,
              private dialog: MatDialog) { }

  ngOnInit() {

    this.paso = 1;

    this.form = new FormGroup({
      'identificadores': new FormArray([])
    });
    this.subscriptionRoute = this.route.queryParams.subscribe((params: any) => {
      this.title = params.desc;
      this.favorito = params.favorito;
    });

    this.subscriptionRoute = this.route.params.subscribe((params: any) => {
        console.log(params);
        this.servicio = params.servicio ? +params.servicio : -1;
        this.servicioService.getIndentificadores(this.servicio).subscribe( (data: Identificador[]) => {
          this.identificadores = data;
          for (let i = 0; i < this.identificadores.length; i++) {
            (<FormArray>this.form.get('identificadores')).push(
              new FormGroup({
                'id': new FormControl(this.identificadores[i].id),
                'nombre': new FormControl(this.identificadores[i].nombre),
                'tipo': new FormControl(this.identificadores[i].tipo),
                'codigo': new FormControl(this.identificadores[i].codigo),
                'valor': new FormControl('', [Validators.required]),
              }));
          }

          if (this.favorito) {
            // setear valores favoritos
            this.favoritoService.obtenerFavoritos(this.favorito).subscribe( (favoritos: Favorito[]) => {
              if (favoritos.length > 0) {
                const fav = favoritos[0];
                this.title = fav.servicio + ' - ' + fav.alias;
                for (const guardado of fav.identificadores) {
                  console.log(guardado);
                  for (const control of (<FormArray>this.form.get('identificadores')).controls) {
                    if (control.get('id').value === guardado.id) {
                      control.get('valor').setValue(guardado.valor);
                    }
                  }
                }
              }

              // validar si todos los valores se setearon y hacer la consulta
              if (this.form.valid) {
                this.consultar();
              }

            }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
          }

        }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
      }
    );

    this.formPay = new FormGroup({
      'cuenta': new FormControl('', [Validators.required]),
    });

    this.cuentaService.obtenerCuentas().subscribe( (data: Cuenta[]) => {
      this.cuentas = data;
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));

  }

  consultar() {
    console.log(this.form.value);
    const identificadores = {};
    if (this.form.value.identificadores) {
      this.form.value.identificadores.forEach( e => identificadores[e.codigo] = e.valor);
    }
    this.service.consulta(this.servicio, identificadores).subscribe( (data: any) => {
      this.saldo = data.saldo;
      this.paso = 2;
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  pagar() {
    const noCuenta = this.formPay.get('cuenta').value;
    this.cuenta = this.cuentas.find(f => f.numero === noCuenta);
    this.paso = 3;
  }

  confirmarPago() {
    const request = {
      servicio: this.servicio,
      cuenta: this.formPay.get('cuenta').value,
      identificadores: {},
    };
    request.identificadores['VALOR'] = this.saldo;
    this.identificadoresUsados['desc'] = this.title;
    if (this.form.value.identificadores) {
      this.form.value.identificadores.forEach( e => {
        request.identificadores[e.codigo] = e.valor;
        this.identificadoresUsados[e.codigo] = e.valor; // para configurar favorito
      });
    }
    this.service.pago(request).subscribe( (data: any) => {
      this.paso = 4;
      this.idPago = data.id;
      this.notifications.success('Operación exitosa', 'Pago realizado exitosamente!');
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }



  ngOnDestroy(): void {
    this.subscriptionRoute.unsubscribe();
  }
}
