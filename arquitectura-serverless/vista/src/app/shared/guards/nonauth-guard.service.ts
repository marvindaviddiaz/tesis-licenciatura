import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {SecurityService} from '../../security/security.service';


// import { AuthService } from './auth.service';

@Injectable()
export class NonAuthGuardService implements CanActivate, CanActivateChild {


    constructor(private securityService: SecurityService, private router: Router) {}

    canActivate():  Observable<boolean> | Promise<boolean> | boolean  {
        // console.log("AuthGuardService.canActivate..");
        return this.validate();
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        // console.log("AuthGuardService.canActivateChild..");
        return this.validate();
    }

    private validate(): Observable<boolean> | Promise<boolean> | boolean {
        let ob = this.securityService.isLoggedOut();
        ob.subscribe((isValid) => {
            console.log("AuthGuardService: " +  isValid);
            if (!isValid) {
                this.router.navigate(['/home']);
            }
            console.log("isValid: " + isValid);
        }, (error) => {
            this.router.navigate(['/home']);
            console.log("error: " + error);
        });
        return ob;
    }

}
