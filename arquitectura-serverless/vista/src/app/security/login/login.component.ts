import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SecurityService} from '../security.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LAST_ACTIVITY, REMEMBER_ME, USER_DISABLED} from '../../shared/constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../../../assets/css/starter.css']
})

export class LoginComponent implements OnInit {

  errorMessage: string;
  form: FormGroup;

  constructor(private router: Router,
              private securityService: SecurityService) {
  }

  ngOnInit() {
    this.errorMessage = null;
    this.form = new FormGroup({
      'email': new FormControl('', [Validators.required]),
      'password': new FormControl('', [Validators.required]),
      'rememberMe': new FormControl('')
    });

  }

  onLogin() {

    const email = this.form.get('email').value.toString().toLowerCase().trim();

    const password = this.form.get('password').value;
    if (email == null || password == null) {
      this.errorMessage = 'Debe ingresar la información';
      return;
    }
    this.errorMessage = null;
    this.securityService.authenticate(email, password).subscribe(value => {
      console.log('Login success');
      const rememberMe = this.form.get('rememberMe').value === true ? 'true' : 'false';
      localStorage.setItem(LAST_ACTIVITY, new Date().getTime().toString());
      localStorage.setItem(REMEMBER_ME, rememberMe);
      this.router.navigate(['/home']);
    }, messageErr => {
      console.log('onFailure...:');
      console.log(messageErr);
      if (messageErr.hasOwnProperty('statusCode') && messageErr.statusCode === 200) {
        return;
      }
      switch (messageErr) {
        case 'UserNotConfirmedException':
          console.log('redirecting');
          this.router.navigate(['/security/confirmRegistration', email]);
          break;
        case 'SetPassword':
          console.log('redirecting to set new password');
          this.router.navigate(['/security/temporaryPassword/param'], {queryParams: {email: email}, skipLocationChange: true});
          break;
        case 'ForgotPassword':
          console.log('redirecting to set new password (forgot)');
          this.router.navigate(['/security/forgotPassword/param'], {queryParams: {email: email}, skipLocationChange: true});
          break;
        default:
          let key: string;
          if (messageErr.name === 'NotAuthorizedException' && messageErr.message === USER_DISABLED) {
            key = 'errorCognito.userDisabled';
          } else {
            key = 'errorCognito.' + messageErr.name;
          }
          this.errorMessage = key;
          break;
      }
    });

  }

}
