import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BuscarServicioService {

  private apiUrl = `${environment.apiUrl}/api/servicios`;

  constructor(private http: HttpClient) { }

  query(query: string): Observable<any> {
    return this.http.get(this.apiUrl, {params : {'busqueda' : query}});
  }
}
