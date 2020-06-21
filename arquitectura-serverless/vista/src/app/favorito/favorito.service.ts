import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
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

}
