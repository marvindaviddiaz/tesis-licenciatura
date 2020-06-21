import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {FavoritoService} from './favorito.service';
import {NotificationsService} from 'angular2-notifications';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Identificador} from '../dominio/Identificador';
import {CuentaService} from '../cuenta/cuenta.service';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpUtilService} from '../shared/util/http-util.service';
import {ServicioService} from '../servicio/servicio.service';

@Component({
  selector: 'app-favorito',
  templateUrl: './favorito.component.html',
  styleUrls: ['./favorito.component.css']
})
export class FavoritoComponent implements OnInit, OnDestroy {

  form: FormGroup;
  identificadores: Identificador[] = [];
  subscriptionRoute: Subscription;
  servicio: number;
  title: string;

  constructor(private service: FavoritoService,
              private servicioService: ServicioService,
              private cuentaService: CuentaService,
              private route: ActivatedRoute,
              private notifications: NotificationsService) { }

  ngOnInit() {

    this.form = new FormGroup({
      'nombre': new FormControl('', [Validators.required]),
      'identificadores': new FormArray([])
    });

    this.subscriptionRoute = this.route.params.subscribe((params: any) => {
      this.servicio = params.servicio ? +params.servicio : -1;
    });

    this.subscriptionRoute = this.route.queryParams.subscribe((params: any) => {
        this.servicioService.getIndentificadores(this.servicio).subscribe( (data: Identificador[]) => {
          this.title = params.desc;
          this.identificadores = data;
          let valorIdentificador = '';
          const paramsNames = Object.getOwnPropertyNames(params);
          for (let i = 0; i < this.identificadores.length; i++) {

            // obtener identificadores recibidos
            for (let j = 0; j < paramsNames.length; j++) {
              if (paramsNames[j] === this.identificadores[i].codigo) {
                valorIdentificador = params[paramsNames[j]];
              }
            }

            (<FormArray>this.form.get('identificadores')).push(
              new FormGroup({
                'id': new FormControl(this.identificadores[i].id),
                'nombre': new FormControl(this.identificadores[i].nombre),
                'tipo': new FormControl(this.identificadores[i].tipo),
                'codigo': new FormControl(this.identificadores[i].codigo),
                'valor': new FormControl(valorIdentificador, [Validators.required]),
              }));
          }
        }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
      }
    );
  }

  guardar() {
    const request = {
      servicio: this.servicio,
      alias: this.form.get('nombre').value,
      identificadores: {},
    };

    if (this.form.value.identificadores) {
      this.form.value.identificadores.forEach( e => request.identificadores[e.codigo] = e.valor);
    }
    this.service.guardar(request).subscribe( (data: any) => {
      this.notifications.success('Operación exitosa', 'Favorito guardado exitosamente!');
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  ngOnDestroy(): void {
    this.subscriptionRoute.unsubscribe();
  }
}
