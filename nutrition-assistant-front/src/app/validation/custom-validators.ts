import {AbstractControl, ValidationErrors} from '@angular/forms';

export class CustomValidators {

  static positive(control: AbstractControl): ValidationErrors | null {
    return control.value <= 0 ? { positive: true } : null;
  }

}
