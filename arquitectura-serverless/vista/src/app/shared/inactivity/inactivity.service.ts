import {Injectable, OnDestroy} from '@angular/core';
import {Observable, Subscription, timer} from 'rxjs';
import {SecurityService} from '../../security/security.service';
import {INACTIVITY_TIMEOUT, LAST_ACTIVITY, REMEMBER_ME} from '../constants';

@Injectable()
export class InactivityService implements OnDestroy {

    private subscription: Subscription;
    private timer: Observable<number>;

    constructor(private securityService: SecurityService) {
        console.log('InactivityService.new');
        this.timer = timer(INACTIVITY_TIMEOUT, INACTIVITY_TIMEOUT);
        this.subscription = this.timer.subscribe(t => {
            this.onInactive();
        });
    }

    public restart() {
        // console.log('InactivityService.restart');
        this.subscription.unsubscribe();
        this.subscription = this.timer.subscribe(t => {
            this.onInactive();
        });
        localStorage.setItem(LAST_ACTIVITY, new Date().getTime().toString());
    }

    private onInactive() {
        const rememberMe = 'true' === localStorage.getItem(REMEMBER_ME);
        if (!rememberMe) {
            this.securityService.validateSession()
                .subscribe(value => {
                    if (value) {
                        console.log('Logout due to inactivity...');
                        this.securityService.logout();
                    }
                });
        }
    }

    ngOnDestroy(): void {
        console.log('InactivityService.destroy');
        this.subscription.unsubscribe();
    }

}
