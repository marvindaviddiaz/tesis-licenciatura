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
import {ConfirmacionPagoDialogComponent} from '../confirmacion-pago/confirmacion-pago';

@Component({
  selector: 'app-consulta-pago',
  templateUrl: './consulta-pago.component.html',
  styleUrls: ['./consulta-pago.component.css']
})
export class ConsultaPagoComponent implements OnInit, OnDestroy {

  form: FormGroup;
  formPay: FormGroup;
  identificadores: Identificador[] = [];
  private subscriptionRoute: Subscription;
  private servicio: number;
  private title: string;
  private saldo: number;
  private cuentas: Cuenta[];

  constructor(private service: ConsultaPagoService,
              private cuentaService: CuentaService,
              private route: ActivatedRoute,
              private notifications: NotificationsService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.form = new FormGroup({
      'identificadores': new FormArray([])
    });
    this.subscriptionRoute = this.route.queryParams.subscribe((params: any) => {
      this.title = params.desc;
    });

    this.subscriptionRoute = this.route.params.subscribe((params: any) => {
      console.log(params);
        this.servicio = params.servicio ? +params.servicio : -1;
        this.service.getIndentificadores(this.servicio).subscribe( (data: Identificador[]) => {
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

  onSubmitSearch() {
    console.log(this.form.value);
    const identificadores = {};
    if (this.form.value.identificadores) {
      this.form.value.identificadores.forEach( e => identificadores[e.codigo] = e.valor);
    }
    this.service.consulta(this.servicio, identificadores).subscribe( (data: any) => {
      this.saldo = data.saldo;
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  openConfirmationDialog(): void {
    const noCuenta = this.formPay.get('cuenta').value;
    const cuenta = this.cuentas.find(f => f.numero === noCuenta);
    const dialogRef = this.dialog.open(ConfirmacionPagoDialogComponent, {
      width: '450px',
      data: {modo: 'confirm', titulo: this.title, valor: this.saldo, cuenta: cuenta.numero + ' - ' + cuenta.alias}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        console.log(result);
        this.onSubmitPay();
      }
    });
  }

  onSubmitPay() {
    const request = {
      servicio: this.servicio,
      cuenta: this.formPay.get('cuenta').value,
      identificadores: {},
    };

    request.identificadores['VALOR'] = this.saldo;
    console.log(request.identificadores);
    if (this.form.value.identificadores) {
      this.form.value.identificadores.forEach( e => request.identificadores[e.codigo] = e.valor);
    }

    this.service.pago(request).subscribe( (data: any) => {
      this.notifications.success('Operación exitosa', 'Operación No.');
      this.openSuccessDialog(data);
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  openSuccessDialog(data): void {
    const noCuenta = this.formPay.get('cuenta').value;
    const cuenta = this.cuentas.find(f => f.numero === noCuenta);
    const dialogRef = this.dialog.open(ConfirmacionPagoDialogComponent, {
      width: '450px',
      data: {modo: 'success', idPago: data.id, titulo: this.title, valor: this.saldo, cuenta: cuenta.numero + ' - ' + cuenta.alias}
    });
    dialogRef.afterClosed().subscribe(result => {
      this.formPay.reset();
      this.form.reset();
    });
  }

  ngOnDestroy(): void {
    this.subscriptionRoute.unsubscribe();
  }

}
