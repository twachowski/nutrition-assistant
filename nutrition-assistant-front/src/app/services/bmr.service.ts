import { Injectable } from '@angular/core';
import { Sex } from '../model/user/sex.enum';
import { UserService } from './user.service';
import * as moment from 'moment';
import { ActivityService } from './activity.service';
import { ActivityLevel } from '../model/user/activity-level.enum';
import { NutrientTargetService } from './nutrient-target.service';

@Injectable({
  providedIn: 'root'
})
export class BMRService {

  constructor(
    private readonly userService: UserService,
    private readonly activityService: ActivityService,
    private readonly nutrientTargetService: NutrientTargetService) { }

  private static getSexCoefficient(sex: Sex) {
    if (sex === Sex.MALE) {
      return 5;
    } else {
      return -161;
    }
  }

  calculateBMR(age: number, height: number, weight: number, sex: Sex) {
    return 10 * weight + 6.25 * height - 5 * age + BMRService.getSexCoefficient(sex);
  }

  initUserCalorieTarget() {
    this.userService.getUserBiometrics().subscribe(
      value => {
        const biometrics = value.userBiometrics;
        const age = moment().diff(biometrics.dateOfBirth, 'year');
        const bmr = this.calculateBMR(age, biometrics.height, biometrics.weight, Sex[biometrics.sex]);
        const activityModifier = this.activityService.getModifier(ActivityLevel[biometrics.activityLevel]);
        const calorieTarget = activityModifier * bmr + biometrics.calorieGoal;
        this.nutrientTargetService.setCalorieTarget(calorieTarget);
      },
      error => {
        console.log(error);
      }
    );
  }

}
