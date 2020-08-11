import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HistoricoService {

  private apiUrl = `${environment.apiUrl}/api/bitacora`;

  constructor(private http: HttpClient) { }

  buscar(fechaInicio: string, fechaFin: string, pagina: string, filtro: string): Observable<any> {
    return this.http.get(this.apiUrl, {params : {fechaInicio, fechaFin, pagina, filtro}});
  }
}
