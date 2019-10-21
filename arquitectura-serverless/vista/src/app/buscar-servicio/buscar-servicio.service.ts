import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Servicio} from "../dominio/Servicio";

@Injectable({
  providedIn: 'root'
})
export class BuscarServicioService {

  private apiUrl = `${environment.apiUrl}/api/servicios/buscar`;

  constructor(private http: HttpClient) { }

  query(query: string): Observable<any> {
    return this.http.post(this.apiUrl, query);
  }
}
