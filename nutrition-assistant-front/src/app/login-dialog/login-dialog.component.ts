import { Component, OnInit } from '@angular/core';
import { DialogWithToolbarComponent } from '../dialog-with-toolbar/dialog-with-toolbar.component';
import { Validators, FormControl } from '@angular/forms';
import { UserService } from '../services/user.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private tabIndex = 0;
  private email = new FormControl('', [Validators.required, Validators.email]);
  private password = new FormControl('', [Validators.required]);
  private emailReset = new FormControl('', [Validators.required, Validators.email]);
  private requestInProgress = false;

  constructor(
    private dialogRef: MatDialogRef<LoginDialogComponent>,
    private userService: UserService) {
    super();
  }

  ngOnInit() {
  }

  logIn() {
    this.requestInProgress = true;
    const credentials = {
      email: this.email.value,
      password: this.password.value
    };
    this.userService.logIn(credentials)
      .subscribe(
        response => {
          localStorage.setItem('jwt', response.jwt);
          this.requestInProgress = false;
          this.dialogRef.close(this.email.value);
        },
        error => {
          this.requestInProgress = false;
        }
      );
  }

  getEmailErrorMessage(control: FormControl) {
    if (control.hasError('required')) {
      return 'Email must not be blank';
    } else if (control.hasError('email')) {
      return 'Invalid email';
    } else {
      return '';
    }
  }

  getEmailLoginErrorMessage() {
    return this.getEmailErrorMessage(this.email);
  }

  getEmailResetErrorMessage() {
    return this.getEmailErrorMessage(this.emailReset);
  }

  getPasswordErrorMessage() {
    if (this.password.hasError('required')) {
      return 'Password must not be blank';
    } else {
      return '';
    }
  }

  isSignInDisabled() {
    return this.email.invalid || this.password.invalid || this.requestInProgress;
  }

  isResetPasswordDisabled() {
    return this.emailReset.invalid || this.requestInProgress;
  }

}
