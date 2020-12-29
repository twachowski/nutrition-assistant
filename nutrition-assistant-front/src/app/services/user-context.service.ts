import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserContextService {

  private loggedUser: string;

  constructor() {
    this.loggedUser = this.extractUserFromToken();
  }

  setLoggedUser(user: string) {
    this.loggedUser = user;
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  private extractUserFromToken() {
    const token = localStorage.getItem('jwt');
    if (!token) {
      return '';
    }
    try {
      const encodedPayload = token.split('.')[1];
      const decodedPayload = atob(encodedPayload);
      const endOfEmail = decodedPayload.indexOf('"', 8);
      const email = decodedPayload.substring(8, endOfEmail);
      return email;
    } catch (error) {
      return '';
    }
  }

}
