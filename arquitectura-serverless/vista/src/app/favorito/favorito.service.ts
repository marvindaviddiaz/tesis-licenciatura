import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FavoritoService {

  private apiUrl = `${environment.apiUrl}/api/favoritos`;

  constructor(private http: HttpClient) { }

  guardar(identificadores: any): Observable<any> {
    return this.http.post(this.apiUrl, identificadores);
  }

  obtenerFavoritos(filtro?: number): Observable<any> {
    let httpParams = new HttpParams();
    if (filtro) {
      httpParams = httpParams.append('filtro', filtro.toString());
    }
    return this.http.get(this.apiUrl, {params: httpParams});
  }

}
