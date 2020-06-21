import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsultaPagoService {

  private consultaUrl = `${environment.apiUrl}/api/consulta`;
  private pagoUrl = `${environment.apiUrl}/api/pago`;

  constructor(private http: HttpClient) { }

  consulta(servicio: number, params: any): Observable<any> {
    return this.http.get(this.consultaUrl + `/${servicio}`, {params: params});
  }

  pago(body: any): Observable<any> {
    return this.http.post(this.pagoUrl, body);
  }

}
