import {AbstractControl, ValidatorFn} from '@angular/forms';
import {isNullOrUndefined} from 'util';

export function customValidator(): ValidatorFn {

  return (control: AbstractControl): { [key: string]: any } => {
    let message = {
      'same': true
    };


    let isValid = !isNullOrUndefined(control.parent) && control.value === control.parent.get('password').value;
    return isValid ? null : message;

  };

}
