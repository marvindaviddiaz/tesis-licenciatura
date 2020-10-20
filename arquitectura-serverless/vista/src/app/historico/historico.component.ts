import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Servicio} from '../dominio/Servicio';
import {Favorito} from '../dominio/Favorito';
import {NotificationsService} from 'angular2-notifications';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpUtilService} from '../shared/util/http-util.service';
import {HistoricoService} from './historico.service';

@Component({
  selector: 'app-historico',
  templateUrl: './historico.component.html',
  styleUrls: ['./historico.component.css']
})
export class HistoricoComponent implements OnInit {

  form: FormGroup;
  list: any[] = [];
  pagina = 1;
  REGISTROS_POR_PAGINA = 10;
  mostrarSiguiente = false;
  mostrarAnterior = false;
  totalRegistros = 0;
  totalPaginas = 0;

  constructor(private service: HistoricoService,
              private notifications: NotificationsService) { }

  ngOnInit() {
    this.form = new FormGroup({
      'inicio': new FormControl(this.toYyyyMMdd(new Date(), true), [Validators.required]),
      'fin': new FormControl(this.toYyyyMMdd(new Date(), false), [Validators.required]),
      'filtro': new FormControl('')
    });
  }

  buscar(pagina) {
    this.pagina = pagina;
    this.service.buscar(this.form.value.inicio, this.form.value.fin, pagina, this.form.value.filtro).subscribe( (data: any) => {
      this.list = data.content;
      this.totalRegistros = data.totalElements;
      this.mostrarAnterior = pagina > 1;
      this.mostrarSiguiente = (this.REGISTROS_POR_PAGINA * pagina) < this.totalRegistros;
      this.totalPaginas = Math.trunc(this.totalRegistros / this.REGISTROS_POR_PAGINA);
      if (this.totalRegistros % this.REGISTROS_POR_PAGINA !== 0) {
        this.totalPaginas = this.totalPaginas + 1;
      }
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }

  anterior() {
    this.buscar(this.pagina - 1);
  }

  siguiente() {
    this.buscar(this.pagina + 1);
  }

  toYyyyMMdd(date: Date, first: boolean): string {
    let day = date.getUTCDate();
    if (first) {
      day = 1;
    }
    return this.toTwo(date.getUTCFullYear()) + '-' + this.toTwo(date.getUTCMonth() + 1 ) + '-' + this.toTwo(day);
  }

  toTwo(value: number) {
    if (value < 10) {
      return '0' + value;
    } else {
      return value;
    }
  }
}
