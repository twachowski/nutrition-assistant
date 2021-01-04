import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoutingService {

  private readonly baseUrl = `${environment.nutritionAssistantServiceBaseUrl}/nutrition-assistant/api/v1.0`;

  private readonly usersUrl = this.baseUrl + '/users';
  private readonly loginUrl = this.usersUrl + '/login';

  private readonly profileUrl = this.baseUrl + '/profile';
  private readonly biometricsUrl = this.profileUrl + '/biometrics';
  private readonly highlightedTargetsUrl = this.profileUrl + '/highlightedTargets';

  private readonly foodsUrl = this.baseUrl + '/foods';

  private readonly exercisesUrl = this.baseUrl + '/exercises';

  private readonly diaryUrl = this.baseUrl + '/diary';
  private readonly entriesSuffix = '/entries';

  constructor() {
  }

  getUsersUrl() {
    return this.usersUrl;
  }

  getLoginUrl() {
    return this.loginUrl;
  }

  getFoodsUrl() {
    return this.foodsUrl;
  }

  getFoodUrl(foodId: string) {
    return this.foodsUrl + `/${foodId}`;
  }

  getExercisesUrl() {
    return this.exercisesUrl;
  }

  getDiaryUrl(date: string) {
    return this.diaryUrl + `/${date}`;
  }

  getEntriesUrl(date: string) {
    return this.getDiaryUrl(date) + this.entriesSuffix;
  }

  getEntryUrl(date: string, entryPosition: number) {
    return this.getEntriesUrl(date) + `/${entryPosition}`;
  }

  getUserBiometricsUrl() {
    return this.biometricsUrl;
  }

  getHighlightedTargetsUrl() {
    return this.highlightedTargetsUrl;
  }

}
