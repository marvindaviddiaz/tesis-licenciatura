import {INACTIVITY_TIMEOUT, LAST_ACTIVITY, REMEMBER_ME} from './shared/constants';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {MatIconRegistry, MatSidenav} from '@angular/material';
import {SecurityService} from './security/security.service';
import {InactivityService} from './shared/inactivity/inactivity.service';
import {Options} from 'angular2-notifications';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  public authorized = false;

  public username: string;
  public name: string;

  public notificationOptions: Options = {
    timeOut: 6000
  };

  @ViewChild('sidenav') sidenav: MatSidenav;

  onCloseMenu(reason: string) {
    this.sidenav.close();
  }


  constructor(private securityService: SecurityService,
              private router: Router,
              private inactivityService: InactivityService,
              private iconRegistry: MatIconRegistry,
              private sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon('pdf', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/pdf.svg'));
    iconRegistry.addSvgIcon('excel', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/excel.svg'));
  }

  ngOnInit() {

    let lastActivity = +localStorage.getItem(LAST_ACTIVITY);
    let rememberMe = 'true' === localStorage.getItem(REMEMBER_ME);
    if (!rememberMe) {
      if ((new Date().getTime() - lastActivity) > INACTIVITY_TIMEOUT) {
        this.securityService.validateSession().subscribe(value => {
          if (value) {
            this.securityService.logout();
          }
        });
      }
    }

    this.subscription = this.router.events.subscribe((val) => {
      if (val instanceof NavigationEnd) {
        this.authorized = !(val.url.startsWith('/security') || val.url.startsWith('/signUp'));
        this.inactivityService.restart();

        if (this.authorized) {
          if (this.username !== this.securityService.getCurrentUser().getUsername()) {
            this.username = this.securityService.getCurrentUser().getUsername();
            this.securityService.getCurrentUserAttributes().subscribe(data => {
              data.filter(f => f.getName() === 'name').forEach(e => this.name = e.getValue());
            }, error => {
              console.log(error);
            });

          }
        }

      }
    });
  }

  ngOnDestroy() {
    console.log('AppComponent.ngOnDestroy');
    this.subscription.unsubscribe();
  }
}
