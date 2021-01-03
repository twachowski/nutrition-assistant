import { Component, OnInit } from '@angular/core';
import { DialogWithToolbarComponent } from '../dialog-with-toolbar/dialog-with-toolbar.component';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css']
})
export class RegisterDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private readonly passwordLength = 12;
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required, Validators.minLength(this.passwordLength)]);
  confirmedPassword = new FormControl('', [Validators.required]);
  termsAccepted = true;
  requestInProgress = false;
  tabIndex = 0;

  constructor(
    private dialogRef: MatDialogRef<RegisterDialogComponent>,
    private userService: UserService) {
    super();
  }

  ngOnInit() {
  }

  getEmailErrorMessage() {
    if (this.email.hasError('required')) {
      return 'Email must not be blank';
    } else if (this.email.hasError('email')) {
      return 'Invalid email';
    } else {
      return '';
    }
  }

  getPasswordErrorMessage() {
    if (this.password.hasError('required')) {
      return 'Password must not be blank';
    } else if (this.password.hasError('minlength')) {
      return `Password should be at least ${this.passwordLength} characters long`;
    } else {
      return '';
    }
  }

  getConfirmedPasswordErrorMessage() {
    if (this.confirmedPassword.hasError('required')) {
      return 'Password confirmation must not be blank';
    } else if (this.confirmedPassword.hasError('pattern')) {
      return 'Password confirmation does not match password';
    } else {
      return '';
    }
  }

  isSignUpUnavailable() {
    return this.email.invalid
      || this.password.invalid
      || this.confirmedPassword.invalid
      || !this.termsAccepted
      || this.requestInProgress;
  }

  signUp() {
    this.requestInProgress = true;
    const credentials = {
      email: this.email.value,
      password: this.password.value,
      passwordConfirmation: this.confirmedPassword.value
    };
    this.userService.signUp(credentials)
      .subscribe(
        data => {
          this.tabIndex = 1;
          this.requestInProgress = false;
        },
        error => {
          this.requestInProgress = false;
        });
  }

}
