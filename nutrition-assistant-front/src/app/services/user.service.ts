import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {RegisterCredentials} from '../model/register-credentials';
import {LoginCredentials} from '../model/login-credentials';
import {UserBiometrics} from '../model/user/user-biometrics';
import {RoutingService} from './routing.service';
import {TokenRequest} from '../model/token-request';
import {TokenResponse} from '../model/token-response';
import {UserBiometricsResponse} from '../model/user/user-biometrics-response';
import {UserBiometricsRequest} from '../model/user/user-biometrics-request';
import {HighlightedTargetsResponse} from '../model/user/highlighted-targets-response';
import {HighlightedTargetsRequest} from '../model/user/highlighted-targets-request';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private readonly http: HttpClient,
    private readonly routingService: RoutingService) {
  }

  signUp(credentials: RegisterCredentials) {
    const url = this.routingService.getUsersUrl();
    const body = JSON.stringify(credentials);
    return this.http.post(url, body);
  }

  activateAccount(token: TokenRequest) {
    const url = this.routingService.getUsersUrl();
    const body = JSON.stringify(token);
    return this.http.patch(url, body);
  }

  logIn(credentials: LoginCredentials) {
    const url = this.routingService.getLoginUrl();
    const body = JSON.stringify(credentials);
    return this.http.post<TokenResponse>(url, body);
  }

  getUserBiometrics() {
    const url = this.routingService.getUserBiometricsUrl();
    return this.http.get<UserBiometricsResponse>(url);
  }

  saveUserBiometrics(biometrics: UserBiometrics) {
    const url = this.routingService.getUserBiometricsUrl();
    const request: UserBiometricsRequest = {
      userBiometrics: biometrics
    };
    const body = JSON.stringify(request);
    return this.http.put(url, body);
  }

  getUserHighlightedTargets() {
    const url = this.routingService.getHighlightedTargetsUrl();
    return this.http.get<HighlightedTargetsResponse>(url);
  }

  saveUserHighlightedTargets(targets: HighlightedTargetsRequest) {
    const url = this.routingService.getHighlightedTargetsUrl();
    const body = JSON.stringify(targets);
    return this.http.put(url, body);
  }

}
