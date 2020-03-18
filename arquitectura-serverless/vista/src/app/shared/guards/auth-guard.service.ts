import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {SecurityService} from '../../security/security.service';

@Injectable()
export class AuthGuardService implements CanActivate, CanActivateChild {


    constructor(private securityService: SecurityService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):  Observable<boolean> | Promise<boolean> | boolean  {
        // console.log("AuthGuardService.canActivate..");
        return this.validate(state);
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        // console.log("AuthGuardService.canActivateChild..");
        return this.validate(state);
    }

    private validate(state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        const ob = this.securityService.validateSession();
        ob.subscribe((isValid) => {
            // console.log("AuthGuardService: " +  isValid);
            if (!isValid) {
                this.router.navigate(['/security/login']);
            }
        }, (error) => {
            this.router.navigate(['/security/login']);
        });
        return ob;
    }

}
