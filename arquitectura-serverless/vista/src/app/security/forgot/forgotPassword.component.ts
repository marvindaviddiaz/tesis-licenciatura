import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {SecurityService} from '../security.service';
import {isUndefined} from 'util';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {PASSWORD_REGEXP} from '../../shared/constants';
import {customValidator} from '../confirm-new-password.validator';

@Component({
  selector: 'app-forgot-password-step1',
  templateUrl: './forgot-password-step-1.component.html',
  styleUrls: ['../login/login.component.css', '../../../assets/css/starter.css']
})
export class ForgotPasswordStep1Component {
  email: string;
  errorMessage: string;
  form: FormGroup;

  constructor(private router: Router,
              private securityService: SecurityService) {
    this.errorMessage = null;
    this.form = new FormGroup({
      'email': new FormControl('', [Validators.required])
    });
  }

  onNext() {
    this.errorMessage = null;
    this.email = this.form.get('email').value.toString().toLowerCase();
    if (isUndefined(this.email) || this.email === null) {
      this.errorMessage = "Debe ingresar la información";
      return;
    }

    this.securityService.forgotPassword(this.email).subscribe(
      data => this.router.navigate(['/security/forgotPassword/new'], {queryParams: {email: this.email}, skipLocationChange: true}),
      messageErr => {
        console.log(messageErr);

        let key: string = 'errorCognito.' + messageErr.name;
        this.errorMessage = key;
      }
    );

  }
}

@Component({
  selector: 'app-forgot-password-step2',
  templateUrl: './forgot-password-step-2.component.html',
  styleUrls: ['../login/login.component.css', '../../../assets/css/starter.css']
})
export class ForgotPasswordStep2Component implements OnInit, OnDestroy {

  verificationCode: string;
  email: string;
  password: string;
  errorMessage: string;
  form: FormGroup;
  private sub: any;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private securityService: SecurityService) {
  }

  ngOnInit() {

    this.sub = this.route.queryParams.subscribe(
      (params: any[]) => {
        this.email = params['email'];
      }
    );

    this.form = new FormGroup({
      'verificationCode': new FormControl('', [Validators.required]),
      'password': new FormControl('', [Validators.required, Validators.pattern(PASSWORD_REGEXP)]),
      'confirmNewPassword': new FormControl('', [customValidator()])
    });
    this.errorMessage = null;
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  onNext() {
    this.errorMessage = null;
    this.verificationCode = this.form.get('verificationCode').value;
    this.password = this.form.get('password').value;
    if (isUndefined(this.verificationCode) || isUndefined(this.password)) {
      this.errorMessage = "Debe ingresar la información";
      return;
    }

    if (this.password) {
      this.securityService.confirmNewPassword(this.email, this.verificationCode, this.password).subscribe(
        data => {
          this.router.navigate(['/home']);
        }, messageErr => {
          let key: string = 'forgotPasswordStep2.' + messageErr.name;
          this.errorMessage = key;
        }
      );
    }
  }

}
