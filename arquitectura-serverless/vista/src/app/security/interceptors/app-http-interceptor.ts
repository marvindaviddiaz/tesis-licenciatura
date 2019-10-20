import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {InactivityService} from '../../shared/inactivity/inactivity.service';
import {SecurityService} from '../security.service';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(private inactivityService: InactivityService,
              private securityService: SecurityService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.securityService.getAccessToken().subscribe(token => {
      if (token != null) {
        request = request.clone({
          setHeaders: {
            'AccessToken': token
          }
        });
      }});

    // this.inactivityService.restart();
    return next.handle(request);
  }
}
