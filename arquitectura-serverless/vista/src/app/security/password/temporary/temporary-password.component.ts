import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SecurityService} from '../../security.service';
import {NewPasswordUser} from '../../new-password-user-model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {PASSWORD_REGEXP} from '../../../shared/constants';
import {Subscription} from 'rxjs';
import {customValidator} from '../../confirm-new-password.validator';

/**
 * This component is responsible for displaying and controlling
 * the registration of the user.
 */
@Component({
  selector: 'app-temporary-password',
  templateUrl: './temporary-password.component.html',
  styleUrls: ['../../login/login.component.css', '../../../../assets/css/starter.css']
})
export class TemporaryPasswordComponent implements OnInit, OnDestroy {
  registrationUser: NewPasswordUser;
  errorMessage: string;
  form: FormGroup;
  private subscription: Subscription;
  email: string;

  constructor(private route: ActivatedRoute,
              private securityService: SecurityService,
              private router: Router) {
  }

  ngOnInit() {
    this.subscription = this.route.queryParams.subscribe(
      (params: any[]) => {
        this.email = params['email'];
        this.registrationUser = new NewPasswordUser();
        this.errorMessage = null;
        console.log('Checking if the user is already authenticated. If so, then redirect to the secure site');
        this.form = new FormGroup({
          'username': new FormControl({value: this.email, disabled: true}),
          'existingPassword': new FormControl('', [Validators.required]),
          'password': new FormControl('', [Validators.required, Validators.pattern(PASSWORD_REGEXP)]),
          'confirmNewPassword': new FormControl('', [customValidator()])
        });
      }
    );

  }

  onSubmit() {
    this.registrationUser.username = this.form.get('username').value;
    this.registrationUser.existingPassword = this.form.get('existingPassword').value;
    this.registrationUser.password = this.form.get('password').value;
    this.errorMessage = null;
    this.securityService.newPassword(this.registrationUser).subscribe(
      data => {

      }, messageErr => {
        const key: string = 'errorCognito.' + messageErr.name;
        this.errorMessage = key;
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
