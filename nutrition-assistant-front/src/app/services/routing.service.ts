import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoutingService {

  private readonly baseUrl = 'http://localhost:8080/api';

  private readonly userUrl = this.baseUrl + '/user';
  private readonly registrationUrl = this.userUrl + '/registration';
  private readonly registrationConfirmUrl = this.registrationUrl + '/confirm';
  private readonly loginUrl = this.userUrl + '/login';

  private readonly foodSearchUrl = this.baseUrl + '/food/search';
  private readonly foodDetailsUrl = this.baseUrl + '/food/details';
  private readonly exerciseSearchUrl = this.baseUrl + '/exercise/search';

  private readonly diaryUrl = this.baseUrl + '/diary';
  private readonly diaryAddUrl = this.diaryUrl + '/add';
  private readonly diaryEditUrl = this.diaryUrl + '/edit';

  private readonly entryDeleteUrl = this.diaryUrl + '/delete';
  private readonly foodAddUrl = this.diaryAddUrl + '/food';
  private readonly exerciseAddUrl = this.diaryAddUrl + '/exercise';
  private readonly noteAddUrl = this.diaryAddUrl + '/note';
  private readonly foodEditUrl = this.diaryEditUrl + '/food';
  private readonly exerciseEditUrl = this.diaryEditUrl + '/exercise';
  private readonly noteEditUrl = this.diaryEditUrl + '/note';

  private readonly reorderUrl = this.diaryUrl + '/reorder';

  private readonly biometricsUrl = this.userUrl + '/biometrics';

  constructor() { }

  getRegistrationUrl() {
    return this.registrationUrl;
  }

  getRegistrationConfirmUrl() {
    return this.registrationConfirmUrl;
  }

  getLoginUrl() {
    return this.loginUrl;
  }

  getFoodSearchUrl() {
    return this.foodSearchUrl;
  }

  getFoodDetailsUrl() {
    return this.foodDetailsUrl;
  }

  getExerciseSearchUrl() {
    return this.exerciseSearchUrl;
  }

  getDiaryUrl() {
    return this.diaryUrl;
  }

  getFoodAddUrl() {
    return this.foodAddUrl;
  }

  getExerciseAddUrl() {
    return this.exerciseAddUrl;
  }

  getNoteAddUrl() {
    return this.noteAddUrl;
  }

  getFoodEditUrl() {
    return this.foodEditUrl;
  }

  getExerciseEditUrl() {
    return this.exerciseEditUrl;
  }

  getNoteEditUrl() {
    return this.noteEditUrl;
  }

  getEntryDeleteUrl() {
    return this.entryDeleteUrl;
  }

  getReorderUrl() {
    return this.reorderUrl;
  }

  getUserBiometricsUrl() {
    return this.biometricsUrl;
  }

}
