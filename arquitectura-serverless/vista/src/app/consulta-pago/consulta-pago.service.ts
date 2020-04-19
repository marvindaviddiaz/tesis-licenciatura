import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsultaPagoService {

  private apiUrl = `${environment.apiUrl}/api/servicios`;
  private consultaUrl = `${environment.apiUrl}/api/consulta`;

  constructor(private http: HttpClient) { }

  getIndentificadores(servicio: number): Observable<any> {
    return this.http.get(this.apiUrl + `/${servicio}/identificadores`);
  }

  consulta(servicio: number, params: any): Observable<any> {
    return this.http.get(this.consultaUrl + `/${servicio}`, {params: params});
  }

}
