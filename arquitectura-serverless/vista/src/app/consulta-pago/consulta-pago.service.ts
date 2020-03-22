import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsultaPagoService {

  private apiUrl = `${environment.apiUrl}/api/servicios`;

  constructor(private http: HttpClient) { }

  query(servicio: number): Observable<any> {
    return this.http.get(this.apiUrl + `/${servicio}/identificadores`);
  }
}
