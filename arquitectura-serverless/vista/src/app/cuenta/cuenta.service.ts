import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  private apiUrl = `${environment.apiUrl}/api/cuentas`;

  constructor(private http: HttpClient) { }

  obtenerCuentas(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
}
