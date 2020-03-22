import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {ConsultaPagoService} from './consulta-pago.service';
import {HttpErrorResponse} from '@angular/common/http';
import {NotificationsService} from 'angular2-notifications';
import {HttpUtilService} from '../shared/util/http-util.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Identificador} from '../dominio/Identificador';

@Component({
  selector: 'app-consulta-pago',
  templateUrl: './consulta-pago.component.html',
  styleUrls: ['./consulta-pago.component.css']
})
export class ConsultaPagoComponent implements OnInit, OnDestroy {

  form: FormGroup;
  identificadores: Identificador[] = [];
  private subscriptionRoute: Subscription;
  private servicio: number;

  constructor(private service: ConsultaPagoService,
              private route: ActivatedRoute,
              private notifications: NotificationsService) { }

  ngOnInit() {
    this.form = new FormGroup({
      'identificadores': new FormArray([])
    });
    this.subscriptionRoute = this.route.params.subscribe((params: any) => {
      console.log(params);
        this.servicio = params.servicio ? +params.servicio : -1;
        this.service.query(this.servicio).subscribe( (data: Identificador[]) => {
          this.identificadores = data;
          for (let i = 0; i < this.identificadores.length; i++) {
            (<FormArray>this.form.get('identificadores')).push(
              new FormGroup({
                'id': new FormControl(this.identificadores[i].id),
                'nombre': new FormControl(this.identificadores[i].nombre),
                'tipo': new FormControl(this.identificadores[i].tipo),
                'valor': new FormControl('', [Validators.required]),
              }));

          }
        }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
      }
    );


  }

  onSubmit() {
    console.log(this.form.value);
    // this.service.query(this.form.value.search).subscribe( (data: Servicio[]) => {
    //   this.servicios = data;
    // }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  ngOnDestroy(): void {
    this.subscriptionRoute.unsubscribe();
  }

}
