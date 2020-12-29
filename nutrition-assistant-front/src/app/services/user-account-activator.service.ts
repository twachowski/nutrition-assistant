import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { UserService } from './user.service';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';
import {TokenRequest} from '../model/token-request';

@Injectable({
  providedIn: 'root'
})
export class UserAccountActivatorService implements Resolve<any> {

  constructor(private readonly userService: UserService) { }

  resolve(route: ActivatedRouteSnapshot) {
    const tokenValue = route.paramMap.get('token');
    const tokenRequest: TokenRequest = {
      token: tokenValue
    };

    return this.userService.activateAccount(tokenRequest)
      .pipe(
        map(() => true),
        catchError(() => of(false))
      );
  }

}
