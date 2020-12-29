import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { UserService } from './user.service';
import { catchError, map } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserAccountActivatorService implements Resolve<any> {

  constructor(private readonly userService: UserService) { }

  resolve(route: ActivatedRouteSnapshot) {
    const token = route.paramMap.get('token');

    return this.userService.activateAccount(token)
      .pipe(
        map(() => true),
        catchError(() => of(false))
      );
  }

}
