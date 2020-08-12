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

  constructor(private service: HistoricoService,
              private notifications: NotificationsService) { }

  ngOnInit() {
    this.form = new FormGroup({
      'inicio': new FormControl(this.toYyyyMMdd(new Date(), true), [Validators.required]),
      'fin': new FormControl(this.toYyyyMMdd(new Date(), false), [Validators.required]),
      'filtro': new FormControl('')
    });
  }

  buscar() {
    this.service.buscar(this.form.value.inicio, this.form.value.fin, '0', this.form.value.filtro).subscribe( (data: any) => {
      this.list = data.content;
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
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
