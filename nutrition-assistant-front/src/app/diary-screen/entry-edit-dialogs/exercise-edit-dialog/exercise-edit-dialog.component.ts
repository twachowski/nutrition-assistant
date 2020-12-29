import { Component, Inject, OnInit } from '@angular/core';
import { DialogWithToolbarComponent } from '../../../dialog-with-toolbar/dialog-with-toolbar.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ExerciseUnit } from '../../../model/units/exercise-unit.enum';
import { EntryDialogData } from '../../../model/entries/entry-dialog-data';
import { FormControl, Validators } from '@angular/forms';
import { CustomValidators } from '../../../validation/custom-validators';
import { DateService } from 'src/app/services/date.service';
import { DiaryService } from 'src/app/services/diary.service';
import { EditedExerciseEntry } from 'src/app/model/diary/edit/edited-exercise-entry';

@Component({
  selector: 'app-exercise-edit-dialog',
  templateUrl: './exercise-edit-dialog.component.html',
  styleUrls: ['./exercise-edit-dialog.component.css']
})
export class ExerciseEditDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private readonly units = Object.entries(ExerciseUnit).map(arr => arr[1]);
  private readonly unitKeys = Object.entries(ExerciseUnit).map(arr => arr[0]);
  private amount = new FormControl(this.data.amount, [Validators.required, CustomValidators.positive]);
  private exerciseUnit = new FormControl(this.data.unit);
  private calories = new FormControl({ value: this.data.calories, disabled: true });

  private requestInProgress = false;

  private readonly caloriesPerMin: number;

  constructor(
    private readonly dialogRef: MatDialogRef<ExerciseEditDialogComponent>,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    @Inject(MAT_DIALOG_DATA) private data: EntryDialogData) {
    super();
    this.amount.markAsTouched();
    this.amount.valueChanges.subscribe(value => this.calculateCalories());
    this.exerciseUnit.valueChanges.subscribe(value => this.convertAmount());
    const amountMin = this.isMinute() ? this.amount.value : this.amount.value * 60;
    this.caloriesPerMin = this.calories.value / amountMin;
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
    const amountMin = this.isMinute() ? this.amount.value : this.amount.value * 60;
    const calories = amountMin * this.caloriesPerMin;
    this.calories.setValue(this.formatValue(calories, 1));
  }

  convertAmount() {
    if (this.amount.invalid) {
      return;
    }
    const currentValue = this.amount.value;
    const newValue = this.isMinute() ? currentValue * 60 : currentValue / 60;
    const formattedValue = this.formatValue(newValue, 2);
    this.amount.setValue(formattedValue);
  }

  isMinute() {
    return this.exerciseUnit.value === ExerciseUnit.MINUTE;
  }

  editExercise() {
    if (this.dataNotChanged()) {
      this.dialogRef.close();
    }
    this.requestInProgress = true;
    const date = this.dateService.getCurrentValue();
    const entryData: EntryDialogData = {
      name: this.data.name,
      position: this.data.position,
      amount: this.amount.value,
      unit: this.exerciseUnit.value,
      calories: this.calories.value
    };
    const editedEntry: EditedExerciseEntry = {
      duration: this.amount.value,
      unit: this.getUnitKey(),
      position: this.data.position
    };
    this.diaryService.editExercise(date, editedEntry)
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

  dataNotChanged() {
    return this.data.amount === this.amount.value
      && this.data.unit === this.exerciseUnit.value;
  }

  getUnitKey() {
    return this.unitKeys.find(unit =>
      ExerciseUnit[unit] === this.exerciseUnit.value
    );
  }

  onBlur() {
    const newValue = this.formatValue(this.amount.value, 2);
    this.amount.setValue(newValue);
  }

  formatValue(value: number, decimalPlaces: number) {
    return Number(value.toFixed(decimalPlaces));
  }

}
