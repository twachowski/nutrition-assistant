import {Component, Inject, OnInit} from '@angular/core';
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from '@angular/material/bottom-sheet';
import {FoodUnit} from 'src/app/model/units/food-unit.enum';
import {FormControl, Validators} from '@angular/forms';
import {CustomValidators} from 'src/app/validation/custom-validators';
import {NutrientBasicInfo} from 'src/app/model/food/nutrients/nutrient-basic-info';
import {GeneralNutrient} from 'src/app/model/food/nutrients/general-nutrient.enum';
import {Macronutrient} from 'src/app/model/food/nutrients/macronutrient.enum';
import {DateService} from 'src/app/services/date.service';
import {EntryService} from 'src/app/services/entry.service';
import {NewFoodEntry} from 'src/app/model/diary/new-food-entry';
import {NutritionDataProvider} from 'src/app/model/food/nutrition-data-provider.enum';
import {DiaryService} from 'src/app/services/diary.service';

export interface FoodDetailsSheetData {
  readonly externalId: string;
  readonly provider: NutritionDataProvider;
  readonly nutrients: NutrientBasicInfo[];
}

export interface FoodDetailsSheetResult {
  readonly amount: number;
  readonly unit: string;
  readonly calories: number;
}

@Component({
  selector: 'app-food-details-sheet',
  templateUrl: './food-details-sheet.component.html',
  styleUrls: ['./food-details-sheet.component.css']
})
export class FoodDetailsSheetComponent implements OnInit {

  readonly units = Object.entries(FoodUnit).map(arr => arr[1]);
  private readonly unitKeys = Object.entries(FoodUnit).map(arr => arr[0]);

  amount = new FormControl(100, [Validators.required, CustomValidators.positive]);
  foodUnit = new FormControl(FoodUnit.GRAM);
  calories = new FormControl({value: 0, disabled: true});
  carbs = new FormControl(0);
  fat = new FormControl(0);
  protein = new FormControl(0);
  readonly nutrientCount: number;

  private readonly caloriesPerGram: number;
  private readonly carbsPerGram: number;
  private readonly fatPerGram: number;
  private readonly proteinPerGram: number;

  constructor(
    private readonly sheetRef: MatBottomSheetRef<FoodDetailsSheetComponent>,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    private readonly entryService: EntryService,
    @Inject(MAT_BOTTOM_SHEET_DATA) private readonly data: FoodDetailsSheetData) {

    const calories = data.nutrients.find(this.isEnergy).amount;
    const carbs = data.nutrients.find(this.isCarbs).amount;
    const fat = data.nutrients.find(this.isFat).amount;
    const protein = data.nutrients.find(this.isProtein).amount;
    this.nutrientCount = data.nutrients.length;

    this.calories.setValue(this.format1DecimalPlace(calories));
    this.carbs.setValue(this.format1DecimalPlace(carbs));
    this.fat.setValue(this.format1DecimalPlace(fat));
    this.protein.setValue(this.format1DecimalPlace(protein));

    this.caloriesPerGram = calories / this.amount.value;
    this.carbsPerGram = carbs / this.amount.value;
    this.fatPerGram = fat / this.amount.value;
    this.proteinPerGram = protein / this.amount.value;

    this.amount.valueChanges.subscribe(value => this.calculateData());
    this.foodUnit.valueChanges.subscribe(value => this.convertAmount());
  }

  ngOnInit() {
  }

  getAmountErrorMessage() {
    if (this.amount.hasError('required')) {
      return 'Amount must be a valid number';
    } else if (this.amount.hasError('positive')) {
      return 'Amount must be positive';
    } else {
      return '';
    }
  }

  calculateData() {
    if (this.amount.invalid) {
      return;
    }
    const amountGrams = this.isGram() ? this.amount.value : this.amount.value * 28.35;
    this.calories.setValue(this.format1DecimalPlace(amountGrams * this.caloriesPerGram));
    this.carbs.setValue(this.format1DecimalPlace(amountGrams * this.carbsPerGram));
    this.fat.setValue(this.format1DecimalPlace(amountGrams * this.fatPerGram));
    this.protein.setValue(this.format1DecimalPlace(amountGrams * this.proteinPerGram));
  }

  convertAmount() {
    if (this.amount.invalid) {
      return;
    }
    const currentValue = this.amount.value;
    const newValue = this.isGram() ? currentValue * 28.35 : currentValue / 28.35;
    const formattedValue = this.format2DecimalPlaces(newValue);
    this.amount.setValue(formattedValue);
  }

  isGram() {
    return this.foodUnit.value === FoodUnit.GRAM;
  }

  isEnergy(nutrientInfo: NutrientBasicInfo) {
    return GeneralNutrient[nutrientInfo.nutrient] === GeneralNutrient.ENERGY;
  }

  isCarbs(nutrientInfo: NutrientBasicInfo) {
    return Macronutrient[nutrientInfo.nutrient] === Macronutrient.TOTAL_CARBS;
  }

  isFat(nutrientInfo: NutrientBasicInfo) {
    return Macronutrient[nutrientInfo.nutrient] === Macronutrient.TOTAL_FAT;
  }

  isProtein(nutrientInfo: NutrientBasicInfo) {
    return Macronutrient[nutrientInfo.nutrient] === Macronutrient.TOTAL_PROTEIN;
  }

  addNewFood() {
    this.sheetRef.disableClose = true;

    const date = this.dateService.getCurrentValue();
    const newFoodEntry: NewFoodEntry = {
      id: this.data.externalId,
      nutritionDataProvider: this.data.provider,
      amount: this.amount.value,
      massUnit: this.getUnitKey()
    };
    const result: FoodDetailsSheetResult = {
      amount: this.amount.value,
      unit: this.foodUnit.value,
      calories: this.calories.value
    };
    this.diaryService.addFoodEntry(date, newFoodEntry)
      .subscribe(
        value => {
          this.recalculateNutrientsAmounts();
          this.sheetRef.dismiss(result);
        },
        error => {
          this.sheetRef.disableClose = false;
          console.log(error);
        }
      );
  }

  getUnitKey() {
    return this.unitKeys.find(unit =>
      FoodUnit[unit] === this.foodUnit.value
    );
  }

  recalculateNutrientsAmounts() {
    const amountGrams = this.isGram() ? this.amount.value : this.amount.value * 28.35;
    const coeff = amountGrams / 100;
    this.data.nutrients.forEach(nutrient => nutrient.amount *= coeff);
  }

  onBlur() {
    const newValue = this.format2DecimalPlaces(this.amount.value);
    this.amount.setValue(newValue);
  }

  format1DecimalPlace(value: number) {
    return Number(value.toFixed(1));
  }

  format2DecimalPlaces(value: number) {
    return Number(value.toFixed(2));
  }

}
