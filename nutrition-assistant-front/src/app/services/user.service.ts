import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { JwtToken } from '../model/jwt-token';
import { RegisterCredentials } from '../model/register-credentials';
import { LoginCredentials } from '../model/login-credentials';
import { UserBiometrics } from '../model/user/user-biometrics';
import { RoutingService } from './routing.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) { }

  signUp(credentials: RegisterCredentials) {
    const url = this.routingService.getRegistrationUrl();
    const body = JSON.stringify(credentials);
    return this.http.post(url, body);
  }

  activateAccount(token: string) {
    const url = this.routingService.getRegistrationConfirmUrl();
    const tokenParam = new HttpParams().set('token', token);
    return this.http.get(url, { params: tokenParam });
  }

  logIn(credentials: LoginCredentials) {
    const url = this.routingService.getLoginUrl();
    const body = JSON.stringify(credentials);
    return this.http.post<JwtToken>(url, body);
  }

  getUserBiometrics() {
    const url = this.routingService.getUserBiometricsUrl();
    return this.http.get<UserBiometrics>(url);
  }

  saveUserBiomterics(biometrics: UserBiometrics) {
    const url = this.routingService.getUserBiometricsUrl();
    const body = JSON.stringify(biometrics);
    return this.http.put(url, body);
  }

}
