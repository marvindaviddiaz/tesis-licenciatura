import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BuscarServicioService} from "./buscar-servicio.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NotificationsService} from "angular2-notifications";
import {HttpUtilService} from "../shared/util/http-util.service";
import {Servicio} from "../dominio/Servicio";
import {Tercero} from "../dominio/Tercero";

@Component({
  selector: 'app-buscar-servicio',
  templateUrl: './buscar-servicio.component.html',
  styleUrls: ['./buscar-servicio.component.css']
})
export class BuscarServicioComponent implements OnInit {

  form: FormGroup;
  servicios: Servicio[] = [];

  constructor(private service: BuscarServicioService,
              private notifications: NotificationsService) { }

  ngOnInit() {
    this.form = new FormGroup({
      'search': new FormControl('', [Validators.required])
    });
  }

  onSubmit() {
    console.log(this.form.value.search)
    this.service.query(this.form.value.search).subscribe( (data: Servicio[]) => {
      this.servicios = data;
    }, (error: HttpErrorResponse) => this.notifications.error('Error', HttpUtilService.handleError(error)));
  }



}
