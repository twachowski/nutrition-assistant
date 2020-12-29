import { Component, OnInit } from '@angular/core';
import { DialogWithToolbarComponent } from 'src/app/dialog-with-toolbar/dialog-with-toolbar.component';
import { MatDialogRef } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';
import { ExerciseDetails } from 'src/app/model/exercise/exercise-details';
import { ExerciseService } from 'src/app/services/exercise.service';
import { CustomValidators } from 'src/app/validation/custom-validators';
import { ExerciseUnit } from 'src/app/model/units/exercise-unit.enum';
import { EntryDialogData } from 'src/app/model/entries/entry-dialog-data';
import { DateService } from 'src/app/services/date.service';
import { DiaryService } from 'src/app/services/diary.service';
import { NewExerciseEntry } from 'src/app/model/diary/add/new-exercise-entry';
import { EntryService } from 'src/app/services/entry.service';

@Component({
  selector: 'app-exercise-search-dialog',
  templateUrl: './exercise-search-dialog.component.html',
  styleUrls: ['./exercise-search-dialog.component.css']
})
export class ExerciseSearchDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private exerciseName = new FormControl('', [Validators.required]);
  private exercises: ExerciseDetails[];
  private readonly displayedColumns = ['name', 'provider'];
  private requestInProgress = false;

  private readonly units = Object.entries(ExerciseUnit).map(arr => arr[1]);
  private readonly unitKeys = Object.entries(ExerciseUnit).map(arr => arr[0]);
  private duration = new FormControl(0, [Validators.required, CustomValidators.positive]);
  private exerciseUnit = new FormControl(ExerciseUnit.MINUTE);
  private calories = new FormControl({ value: 0, disabled: true });

  private selectedExercise: ExerciseDetails;
  private selectedExerciseIndex: number;

  constructor(
    private readonly dialogRef: MatDialogRef<ExerciseSearchDialogComponent>,
    private readonly exerciseService: ExerciseService,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    private readonly entryService: EntryService) {
    super();
    this.duration.valueChanges.subscribe(value => this.calculateCalories());
    this.exerciseUnit.valueChanges.subscribe(value => this.convertDuration());
    this.duration.markAsTouched();
  }

  ngOnInit() {
  }

  search() {
    this.selectedExercise = null;
    this.requestInProgress = true;
    this.exerciseService.search(this.exerciseName.value)
      .subscribe(
        data => {
          this.exercises = data.exercises;
          this.requestInProgress = false;
        },
        error => {
          console.log(error);
          this.exercises = [];
          this.requestInProgress = false;
        }
      );
  }

  getFormattedValue(value: number) {
    return Number(value.toFixed(2));
  }

  getDurationErrorMessage() {
    if (this.duration.hasError('required')) {
      return 'Duration must be a valid number';
    } else if (this.duration.hasError('positive')) {
      return 'Duration must be positive';
    } else {
      return '';
    }
  }

  onRowClick(exercise: ExerciseDetails, index: number) {
    if (this.selectedExercise === exercise) {
      this.selectedExercise = null;
    } else {
      this.selectedExercise = exercise;
      this.selectedExerciseIndex = index;
      this.exerciseUnit.setValue(ExerciseUnit.MINUTE);
      const formattedDuration = this.getFormattedValue(this.selectedExercise.durationMin);
      this.duration.setValue(formattedDuration);
      this.calories.setValue(Number(this.selectedExercise.calories.toFixed(1)));
    }
  }

  onBlur() {
    const newValue = this.getFormattedValue(this.duration.value);
    this.duration.setValue(newValue);
  }

  calculateCalories() {
    const durationMin = this.isMinute() ? this.duration.value : this.duration.value * 60;
    const calories = durationMin / this.selectedExercise.durationMin * this.selectedExercise.calories;
    this.calories.setValue(Number(calories.toFixed(1)));
  }

  convertDuration() {
    const currentValue = this.duration.value;
    const newValue = this.isMinute() ? currentValue * 60 : currentValue / 60;
    const formattedValue = this.getFormattedValue(newValue);
    this.duration.setValue(formattedValue);
  }

  isMinute() {
    return this.exerciseUnit.value === ExerciseUnit.MINUTE;
  }

  addNewExercise() {
    const date = this.dateService.getCurrentValue();
    const entryPosition = this.entryService.getCurrentEntryCount();
    const newExerciseData: EntryDialogData = {
      name: this.selectedExercise.name,
      amount: this.duration.value,
      unit: this.exerciseUnit.value,
      calories: this.calories.value,
      position: entryPosition
    };
    const newExerciseEntry: NewExerciseEntry = {
      name: newExerciseData.name,
      amount: newExerciseData.amount,
      unit: this.getUnitKey(),
      position: entryPosition
    };
    this.diaryService.addExerciseEntry(date, newExerciseEntry)
      .subscribe(
        value => {
          this.entryService.setExerciseEntry(newExerciseData);
          this.dialogRef.close(newExerciseData);
        },
        error => console.log(error)
      );
  }

  getUnitKey() {
    return this.unitKeys.find(unit =>
      ExerciseUnit[unit] === this.exerciseUnit.value
    );
  }

}
