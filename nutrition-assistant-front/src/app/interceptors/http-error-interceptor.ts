import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {ErrorMessage} from '../model/error-message';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ErrorSnackBarComponent} from '../error-snack-bar/error-snack-bar.component';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(
    private readonly router: Router,
    private readonly snackBar: MatSnackBar) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 403 || error.status === 401) {
            localStorage.removeItem('jwt');
            this.router.navigate(['home']);
          } else {
            let message = '';
            if (HttpErrorInterceptor.isErrorMessage(error)) {
              message = (error.error as ErrorMessage).message;
            } else {
              message = 'Unknown error';
            }
            this.snackBar.openFromComponent(ErrorSnackBarComponent, {
              data: message,
              duration: 2000,
              panelClass: 'error-snack-bar'
            });
          }
          return throwError(error.error);
        })
      );
  }

  private static isErrorMessage(error: HttpErrorResponse) {
    return (error.error as ErrorMessage).message !== undefined;
  }

}
