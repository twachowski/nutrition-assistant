import {Component, Inject, OnInit} from '@angular/core';
import {DialogWithToolbarComponent} from '../../../dialog-with-toolbar/dialog-with-toolbar.component';
import {FoodUnit} from '../../../model/units/food-unit.enum';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {EntryDialogData} from '../../../model/entries/entry-dialog-data';
import {FormControl, Validators} from '@angular/forms';
import {CustomValidators} from '../../../validation/custom-validators';
import {DateService} from 'src/app/services/date.service';
import {DiaryService} from 'src/app/services/diary.service';
import {EditedFoodEntry} from 'src/app/model/diary/edited-food-entry';

@Component({
  selector: 'app-food-edit-dialog',
  templateUrl: './food-edit-dialog.component.html',
  styleUrls: ['./food-edit-dialog.component.css']
})
export class FoodEditDialogComponent extends DialogWithToolbarComponent implements OnInit {

  readonly units = Object.entries(FoodUnit).map(arr => arr[1]);
  private readonly unitKeys = Object.entries(FoodUnit).map(arr => arr[0]);
  amount = new FormControl(this.data.amount, [Validators.required, CustomValidators.positive]);
  foodUnit = new FormControl(this.data.unit);
  calories = new FormControl({value: this.data.calories, disabled: true});

  requestInProgress = false;

  private readonly caloriesPerGram: number;

  constructor(
    private readonly dialogRef: MatDialogRef<FoodEditDialogComponent>,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    @Inject(MAT_DIALOG_DATA) public data: EntryDialogData) {
    super();
    this.amount.markAsTouched();
    this.amount.valueChanges.subscribe(value => this.calculateCalories());
    this.foodUnit.valueChanges.subscribe(value => this.convertAmount());
    const amountGrams = this.isGram() ? this.amount.value : this.amount.value * 28.35;
    this.caloriesPerGram = this.calories.value / amountGrams;
  }

  ngOnInit() {
  }

  getErrorMessage() {
    if (this.amount.hasError('required')) {
      return 'Amount must be a valid number';
    } else if (this.amount.hasError('positive')) {
      return 'Amount must be positive';
    } else {
      return '';
    }
  }

  calculateCalories() {
    if (this.amount.invalid) {
      return;
    }
    const amountGrams = this.isGram() ? this.amount.value : this.amount.value * 28.35;
    const calories = amountGrams * this.caloriesPerGram;
    this.calories.setValue(this.formatValue(calories, 1));
  }

  convertAmount() {
    if (this.amount.invalid) {
      return;
    }
    const currentValue = this.amount.value;
    const newValue = this.isGram() ? currentValue * 28.35 : currentValue / 28.35;
    this.amount.setValue(this.formatValue(newValue, 2));
  }

  isGram() {
    return this.foodUnit.value === FoodUnit.GRAM;
  }

  editFood() {
    if (this.dataNotChanged()) {
      this.dialogRef.close();
    }
    this.requestInProgress = true;
    const date = this.dateService.getCurrentValue();
    const entryData: EntryDialogData = {
      name: this.data.name,
      position: this.data.position,
      amount: this.amount.value,
      unit: this.foodUnit.value,
      calories: this.calories.value
    };
    const editedEntry: EditedFoodEntry = {
      amount: this.amount.value,
      massUnit: this.getUnitKey()
    };
    this.diaryService.editFood(date, this.data.position, editedEntry)
      .subscribe(
        value => {
          this.requestInProgress = false;
          this.dialogRef.close(entryData);
        },
        error => {
          this.requestInProgress = false;
          console.log(error);
        }
      );
  }

  getUnitKey() {
    return this.unitKeys.find(unit =>
      FoodUnit[unit] === this.foodUnit.value
    );
  }

  dataNotChanged() {
    return this.data.amount === this.amount.value
      && this.data.unit === this.foodUnit.value;
  }

  onBlur() {
    const newValue = this.formatValue(this.amount.value, 2);
    this.amount.setValue(newValue);
  }

  formatValue(value: number, decimalPlaces: number) {
    return Number(value.toFixed(decimalPlaces));
  }

}
