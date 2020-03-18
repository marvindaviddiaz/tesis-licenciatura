import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SecurityService} from '../../security.service';
import {NewPasswordUser} from '../../new-password-user-model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {NotificationsService} from 'angular2-notifications';
import {PASSWORD_REGEXP} from '../../../shared/constants';

/**
 * This component is responsible for displaying and controlling
 * the registration of the user.
 */
@Component({
    selector: 'app-existing-password',
    templateUrl: './existing-password.component.html',
    styleUrls: ['../../../../assets/css/entities.css']
})
export class ExistingPasswordComponent implements OnInit {
    form: FormGroup;
    isSuccess = false;
    registrationUser: NewPasswordUser;
    errorMessage: string;

    constructor(private securityService: SecurityService,
                private router: Router,
                private _notifications: NotificationsService) {

    }

    ngOnInit() {
        this.registrationUser = new NewPasswordUser();
        this.errorMessage = null;
        this.isSuccess = false;
        this.form = new FormGroup({
            'currentPassword': new FormControl('', [Validators.required]),
            'newPassword': new FormControl('', [Validators.required, Validators.pattern(PASSWORD_REGEXP)]),
            'newPasswordConfirmation': new FormControl('', [Validators.required, Validators.pattern(PASSWORD_REGEXP)])
        });
    }

    private lockForm() {
        this.form.get('currentPassword').disable();
        this.form.get('newPassword').disable();
        this.form.get('newPasswordConfirmation').disable();
    }

    onSubmit() {
        console.log('onSubmit');
        if (this.form.valid) {
            this.registrationUser.existingPassword = this.form.get('currentPassword').value;
            this.registrationUser.password = this.form.get('newPassword').value;
            this.securityService.changePassword(this.registrationUser).subscribe(
                data =>  this.successSubmit(),
                messageErr => {
                    const key: string = 'errorCognito.' + messageErr.name;
                    this.errorMessage = key;
                }
            );
        }
    }

    private successSubmit() {
        this.isSuccess = true;
        this.lockForm();
    }

    onCancel() {
        this.router.navigate(['/home']);
    }
}
