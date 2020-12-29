import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivityLevel } from 'src/app/model/user/activity-level.enum';
import { Sex } from 'src/app/model/user/sex.enum';
import * as moment from 'moment';
import { ActivityService } from 'src/app/services/activity.service';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { CustomValidators } from 'src/app/validation/custom-validators';
import { BMRService } from 'src/app/services/bmr.service';
import { UserService } from 'src/app/services/user.service';
import { UserBiometrics } from 'src/app/model/user/user-biometrics';
import { NutrientTargetService } from 'src/app/services/nutrient-target.service';

const DATE_FORMATS = {
  parse: {
    dateInput: 'DD-MM-YYYY'
  },
  display: {
    dateInput: 'DD-MM-YYYY',
    monthYearLabel: 'MMM YYYY'
  },
};

@Component({
  selector: 'app-biometrics-settings',
  templateUrl: './biometrics-settings.component.html',
  styleUrls: ['./biometrics-settings.component.css'],
  providers: [{ provide: MAT_DATE_FORMATS, useValue: DATE_FORMATS }]
})
export class BiometricsSettingsComponent implements OnInit {

  private readonly minDate = moment('1900-01-01');
  private readonly maxDate = moment();

  private readonly sexKeys = Object.entries(Sex).map(arr => arr[0]);
  private readonly activityKeys = Object.entries(ActivityLevel).map(arr => arr[0]);

  private requestInProgress = false;
  private readonly dateOfBirth = new FormControl(moment(), [Validators.required]);
  private readonly height = new FormControl(180, [Validators.required, Validators.min(1)]);
  private readonly weight = new FormControl(80, [Validators.required, CustomValidators.positive]);
  private readonly sex = new FormControl(Sex.M);
  private readonly activityLevel = new FormControl(ActivityLevel.NONE);
  private readonly sexEntries = Object.entries(Sex).map(arr => arr[1]);
  private readonly activityEntries = Object.entries(ActivityLevel).map(arr => arr[1]);

  private checkedRadio = 1;
  private readonly calorieDeficitValidators = [Validators.required, Validators.min(1), Validators.max(0)];
  private readonly calorieDeficit = new FormControl(0, this.calorieDeficitValidators);
  private readonly calorieSurplus = new FormControl(0, [Validators.required, Validators.min(1)]);

  private readonly biometricsBMR = new FormControl(0);
  private readonly activityBonus = new FormControl(0);
  private readonly goalBonus = new FormControl(0);

  constructor(
    private readonly userService: UserService,
    private readonly activityService: ActivityService,
    private readonly bmrService: BMRService,
    private readonly nutrientTargetService: NutrientTargetService) {
    this.dateOfBirth.valueChanges.subscribe(() => this.calculateBMR());
    this.height.valueChanges.subscribe(() => this.calculateBMR());
    this.weight.valueChanges.subscribe(() => this.calculateBMR());
    this.sex.valueChanges.subscribe(() => this.calculateBMR());
    this.activityLevel.valueChanges.subscribe(() => this.calculateBMR());
    this.calorieDeficit.valueChanges.subscribe(value => this.updateGoal(-value));
    this.calorieSurplus.valueChanges.subscribe(value => this.updateGoal(value));
    this.biometricsBMR.valueChanges.subscribe(value => this.updateCalorieDeficitMaxValidator());
    this.activityBonus.valueChanges.subscribe(value => this.updateCalorieDeficitMaxValidator());
    this.onRadioClick(1);
    this.calculateBMR();
    this.requestInProgress = true;
    this.userService.getUserBiometrics().subscribe(
      data => {
        this.dateOfBirth.setValue(data.dateOfBirth);
        this.height.setValue(data.height);
        this.weight.setValue(data.weight);
        this.sex.setValue(Sex[data.sex]);
        this.activityLevel.setValue(ActivityLevel[data.activityLevel]);
        this.initRadio(data.calorieGoal);
        this.requestInProgress = false;
      },
      error => {
        console.log(error);
        this.requestInProgress = false;
      }
    );
  }

  ngOnInit() {
  }

  inputInvalid() {
    return this.dateOfBirth.invalid
      || this.height.invalid
      || this.weight.invalid
      || this.calorieDeficit.invalid
      || this.calorieSurplus.invalid;
  }

  updateCalorieDeficitMaxValidator() {
    this.calorieDeficitValidators[2] = Validators.max(this.biometricsBMR.value + this.activityBonus.value - 1);
    this.calorieDeficit.setValidators(this.calorieDeficitValidators);
    if (this.calorieDeficit.enabled) {
      this.calorieDeficit.updateValueAndValidity();
    }
  }

  getDateErrorMessage() {
    if (this.dateOfBirth.hasError('required')) {
      return 'Invalid date';
    } else {
      return 'Date does not fit in the available range';
    }
  }

  getGoalErrorMessage(control: FormControl) {
    if (control.hasError('required')) {
      return 'Value is required';
    } else if (control.hasError('min')) {
      return 'Value cannot be lower than 1';
    } else if (control.hasError('max')) {
      return 'Value must be lower than your total BMR';
    } else {
      return '';
    }
  }

  initRadio(calorieGoal: number) {
    let radio: number;
    if (calorieGoal < 0) {
      radio = 0;
      this.calorieDeficit.setValue(-calorieGoal);
    } else if (calorieGoal === 0) {
      radio = 1;
    } else {
      radio = 2;
      this.calorieSurplus.setValue(calorieGoal);
    }
    this.onRadioClick(radio);
  }

  onRadioClick(radio: number) {
    this.checkedRadio = radio;
    if (radio === 0) {
      this.calorieDeficit.enable();
      this.calorieSurplus.disable();
      this.goalBonus.setValue(-this.calorieDeficit.value);
    } else if (radio === 1) {
      this.calorieDeficit.disable();
      this.calorieSurplus.disable();
      this.goalBonus.setValue(0);
    } else {
      this.calorieDeficit.disable();
      this.calorieSurplus.enable();
      this.goalBonus.setValue(this.calorieSurplus.value);
    }
  }

  updateGoal(value: number) {
    this.goalBonus.setValue(value);
  }

  calculateBMR() {
    const age = moment().diff(this.dateOfBirth.value, 'year');
    const bmr = this.bmrService.calculateBMR(
      age,
      this.height.value,
      this.weight.value,
      this.sex.value);
    const activityBonus = (this.activityService.getModifier(this.activityLevel.value) - 1) * bmr;
    this.biometricsBMR.setValue(Math.round(bmr));
    this.activityBonus.setValue(Math.round(activityBonus));
  }

  saveBiometrics() {
    this.requestInProgress = true;
    const userBiometrics: UserBiometrics = {
      dateOfBirth: this.dateOfBirth.value,
      sex: this.getSexKey(),
      height: this.height.value,
      weight: this.weight.value,
      activityLevel: this.getActivityLevelKey(),
      calorieGoal: this.goalBonus.value
    };
    this.userService.saveUserBiomterics(userBiometrics)
      .subscribe(value => {
        this.requestInProgress = false;
        const calorieTarget = this.biometricsBMR.value + this.activityBonus.value + this.goalBonus.value;
        this.nutrientTargetService.setCalorieTarget(calorieTarget);
      },
        error => {
          this.requestInProgress = false;
        });
  }

  getSexKey() {
    return this.sexKeys.find(sex =>
      Sex[sex] === this.sex.value
    );
  }

  getActivityLevelKey() {
    return this.activityKeys.find(level =>
      ActivityLevel[level] === this.activityLevel.value
    );
  }

  onWeightBlur() {
    const weight = Number(this.weight.value);
    this.weight.setValue(weight.toFixed(2));
  }

  onHeightBlur() {
    const height = this.height.value;
    this.height.setValue(Math.round(height));
  }

  onBlur(control: FormControl) {
    const value = control.value;
    control.setValue(Math.round(value));
  }

}
