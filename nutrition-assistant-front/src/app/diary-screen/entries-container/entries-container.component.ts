import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild, ViewRef} from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {MatTable} from '@angular/material/table';
import {Entry} from 'src/app/model/entries/entry';
import {FoodEntry} from 'src/app/model/entries/food-entry';
import {ExerciseEntry} from 'src/app/model/entries/exercise-entry';
import {NoteEntry} from 'src/app/model/entries/note-entry';
import {EntryType} from '../../model/entries/entry-type.enum';
import {MatDialog} from '@angular/material/dialog';
import {
  NoteEditDialogComponent,
  NoteEditDialogData
} from '../entry-edit-dialogs/note-edit-dialog/note-edit-dialog.component';
import {FoodEditDialogComponent} from '../entry-edit-dialogs/food-edit-dialog/food-edit-dialog.component';
import {ExerciseEditDialogComponent} from '../entry-edit-dialogs/exercise-edit-dialog/exercise-edit-dialog.component';
import {DialogConfigService} from '../../services/dialog-config.service';
import {DiaryService} from 'src/app/services/diary.service';
import {FoodEntryDetails} from 'src/app/model/diary/food-entry-details';
import {NutrientBasicInfo} from 'src/app/model/food/nutrients/nutrient-basic-info';
import {GeneralNutrient} from 'src/app/model/food/nutrients/general-nutrient.enum';
import {ExerciseEntryDetails} from 'src/app/model/diary/exercise-entry-details';
import {NoteEntryDetails} from 'src/app/model/diary/note-entry-details';
import {DiaryEntriesResponse} from 'src/app/model/diary/diary-entries-response';
import {ExerciseUnit} from 'src/app/model/units/exercise-unit.enum';
import {FoodUnit} from 'src/app/model/units/food-unit.enum';
import {DateService} from 'src/app/services/date.service';
import {EntryService} from 'src/app/services/entry.service';
import {EntryDialogData} from 'src/app/model/entries/entry-dialog-data';
import {NutrientProgressService} from 'src/app/services/nutrient-progress.service';
import {NutrientDetailsService} from 'src/app/services/nutrient-details.service';
import {NutrientDetailsType} from 'src/app/model/nutrient-details-type.enum';
import {BMRService} from 'src/app/services/bmr.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-entries-container',
  templateUrl: './entries-container.component.html',
  styleUrls: ['./entries-container.component.css']
})
export class EntriesContainerComponent implements OnInit, OnDestroy {

  @ViewChild('table') table: MatTable<Entry>;
  private entries: Entry[] = [];
  private readonly totalNutrients = new Map<NutrientDetailsType, NutrientBasicInfo[]>([
    [NutrientDetailsType.GENERAL, []],
    [NutrientDetailsType.MACRONUTRIENTS, []],
    [NutrientDetailsType.CARBOHYDRATES, []],
    [NutrientDetailsType.LIPIDS, []],
    [NutrientDetailsType.AMINO_ACIDS, []],
    [NutrientDetailsType.VITAMINS, []],
    [NutrientDetailsType.MINERALS, []],
  ]);
  private readonly displayedColumns = ['icon', 'name', 'amount', 'unit', 'calories', 'edit', 'delete'];
  private readonly editFunctionMap: Map<EntryType, (entry: Entry, position: number) => void>;

  private requestInProgress = false;

  private subscription: Subscription;

  constructor(
    private readonly dialog: MatDialog,
    private readonly dialogConfigService: DialogConfigService,
    private readonly diaryService: DiaryService,
    private readonly dateService: DateService,
    private readonly entryService: EntryService,
    private readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService,
    private readonly bmrService: BMRService,
    private readonly changeDetector: ChangeDetectorRef) {
    this.nutrientDetailsService.getAllNutrients()
      .forEach((type, nutrient) =>
        this.totalNutrients.get(type).push({
          nutrient,
          amount: 0
        }));
    this.editFunctionMap = new Map([
      [EntryType.FOOD, this.editFood],
      [EntryType.EXERCISE, this.editExercise],
      [EntryType.NOTE, this.editNote]
    ]);
    this.subscription = this.dateService.getDate().subscribe(
      date => {
        this.requestInProgress = true;
        this.diaryService.getDiaryEntries(date).subscribe(
          data => {
            this.setData(data);
            this.requestInProgress = false;
          },
          error => {
            console.log(error);
            this.requestInProgress = false;
          }
        );
      }
    );
    const exerciseSub = this.entryService.getExerciseEntry().subscribe(entry => {
      const exerciseEntry = new ExerciseEntry(
        entry.name,
        entry.unit,
        entry.amount,
        entry.calories);
      this.entries.push(exerciseEntry);
      this.entryService.setEntryCount(this.entries.length);
      this.updateExerciseCalories();
      this.table.renderRows();
    });
    const foodSub = this.entryService.getFoodEntry().subscribe(entry => {
      const foodEntry = new FoodEntry(
        entry.name,
        entry.unit,
        entry.amount,
        entry.calories,
        entry.nutrients
      );
      this.entries.push(foodEntry);
      this.addNutrients(entry.nutrients);
      this.updateProgress();
      this.entryService.setEntryCount(this.entries.length);
      this.table.renderRows();
    });
    const noteSub = this.entryService.getNoteEntry().subscribe(entry => {
      const noteEntry = new NoteEntry(entry);
      this.entries.push(noteEntry);
      this.entryService.setEntryCount(this.entries.length);
      this.table.renderRows();
    });
    this.subscription.add(foodSub);
    this.subscription.add(exerciseSub);
    this.subscription.add(noteSub);
    this.bmrService.initUserCalorieTarget();
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setData(data: DiaryEntriesResponse) {
    const entriesCount = data.foodEntries.length + data.exerciseEntries.length + data.noteEntries.length;
    this.entries = new Array(entriesCount);
    this.entryService.setEntryCount(entriesCount);
    data.foodEntries.forEach(this.mapFoodEntry, this);
    data.exerciseEntries.forEach(this.mapExerciseEntry, this);
    data.noteEntries.forEach(this.mapNoteEntry, this);
    this.resetProgress();
    this.sumNutrients();
    this.updateProgress();
    this.updateExerciseCalories();
    if (!(this.changeDetector as ViewRef).destroyed) {
      this.changeDetector.detectChanges();
    }
  }

  drop(event: CdkDragDrop<Entry[]>) {
    const prevIndex = this.entries.findIndex((e) => e === event.item.data);
    const currIndex = event.currentIndex;
    if (prevIndex === currIndex) {
      return;
    }
    const date = this.dateService.getCurrentValue();
    moveItemInArray(this.entries, prevIndex, currIndex);
    this.table.renderRows();
    this.requestInProgress = true;
    this.diaryService.reorderEntries(date, prevIndex, currIndex)
      .subscribe(
        value => this.requestInProgress = false,
        error => {
          moveItemInArray(this.entries, currIndex, prevIndex);
          this.table.renderRows();
          this.requestInProgress = false;
          console.log(error);
        }
      );
  }

  delete(index: number) {
    this.requestInProgress = true;
    const date = this.dateService.getCurrentValue();
    this.diaryService.deleteEntry(date, index)
      .subscribe(
        value => {
          this.requestInProgress = false;
          const entry = this.entries[index];
          if (entry.type === EntryType.FOOD) {
            this.removeNutrients((entry as FoodEntry).nutrients);
            this.updateProgress();
          }
          this.entries.splice(index, 1);
          this.entryService.setEntryCount(this.entries.length);
          if (this.isExercise(entry)) {
            this.updateExerciseCalories();
          }
          this.table.renderRows();
        },
        error => {
          this.requestInProgress = false;
          console.log(error);
        }
      );
  }

  edit(entry: Entry, index: number) {
    this.editFunctionMap.get(entry.type).call(this, entry, index);
  }

  editNote(entry: Entry, index: number) {
    const data: NoteEditDialogData = {
      content: entry.name,
      position: index
    };
    const dialog = this.dialog.open(NoteEditDialogComponent, this.dialogConfigService.getEntryEditDialogConfig(data));
    dialog.afterClosed().subscribe(
      result => {
        if (result) {
          entry.name = result;
        }
      });
  }

  editFood(entry: Entry, index: number) {
    const data: EntryDialogData = {
      name: entry.name,
      position: index,
      amount: entry.amount,
      unit: entry.unit,
      calories: entry.calories
    };
    const dialog = this.dialog.open(FoodEditDialogComponent, this.dialogConfigService.getEntryEditDialogConfig(data));
    dialog.afterClosed().subscribe(
      result => {
        if (result) {
          const oldAmountGrams = this.convertToGrams(entry.amount, entry.unit);
          const newAmountGrams = this.convertToGrams(result.amount, result.unit);
          this.editNutrients(index, newAmountGrams / oldAmountGrams);
          this.updateProgress();
          entry.amount = result.amount;
          entry.unit = result.unit;
          entry.calories = result.calories;
        }
      });
  }

  editExercise(entry: Entry, index: number) {
    const data: EntryDialogData = {
      name: entry.name,
      position: index,
      amount: entry.amount,
      unit: entry.unit,
      calories: entry.calories
    };
    const dialog = this.dialog.open(ExerciseEditDialogComponent, this.dialogConfigService.getEntryEditDialogConfig(data));
    dialog.afterClosed().subscribe(
      result => {
        if (result) {
          entry.amount = result.amount;
          entry.unit = result.unit;
          entry.calories = result.calories;
          this.updateExerciseCalories();
        }
      });
  }

  mapFoodEntry(entry: FoodEntryDetails) {
    const foodName = entry.name;
    if (entry.brand) {
      foodName.concat(', ', entry.brand);
    }
    const calories = entry.nutrients.find(this.isEnergy).amount;
    entry.nutrients.forEach(n => {
      n.nutrient = this.nutrientDetailsService.mapNutrientName(n.nutrient);
    });
    this.entries[entry.position] =
      new FoodEntry(
        foodName,
        FoodUnit[entry.massUnit],
        entry.amount,
        Math.round(calories),
        entry.nutrients);
  }

  mapExerciseEntry(entry: ExerciseEntryDetails) {
    this.entries[entry.position] =
      new ExerciseEntry(
        entry.name,
        ExerciseUnit[entry.timeUnit],
        entry.duration,
        Math.round(entry.calories));
  }

  mapNoteEntry(entry: NoteEntryDetails) {
    this.entries[entry.position] = new NoteEntry(entry.content);
  }

  isEnergy(nutrientInfo: NutrientBasicInfo) {
    return GeneralNutrient[nutrientInfo.nutrient] === GeneralNutrient.ENERGY;
  }

  sumNutrients() {
    this.entries.filter(entry => entry.type === EntryType.FOOD)
      .forEach(foodEntry => {
        (foodEntry as FoodEntry).nutrients.forEach(nutrient => {
          const type = this.nutrientDetailsService.getNutrientType(nutrient.nutrient);
          this.totalNutrients.get(type).find(n => n.nutrient === nutrient.nutrient).amount += nutrient.amount;
        });
      });
  }

  addNutrients(nutrients: NutrientBasicInfo[]) {
    nutrients.forEach(nutrient => {
      const name = this.nutrientDetailsService.mapNutrientName(nutrient.nutrient);
      nutrient.nutrient = name;
      const type = this.nutrientDetailsService.getNutrientType(name);
      this.totalNutrients.get(type).find(n => n.nutrient === name).amount += nutrient.amount;
    });
  }

  editNutrients(index: number, coeff: number) {
    (this.entries[index] as FoodEntry).nutrients.forEach(nutrient => {
      const newAmount = nutrient.amount * coeff;
      const diff = newAmount - nutrient.amount;
      nutrient.amount = newAmount;
      const type = this.nutrientDetailsService.getNutrientType(nutrient.nutrient);
      this.totalNutrients.get(type).find(n => n.nutrient === nutrient.nutrient).amount += diff;
    });
  }

  removeNutrients(nutrients: NutrientBasicInfo[]) {
    nutrients.forEach(nutrient => {
      const type = this.nutrientDetailsService.getNutrientType(nutrient.nutrient);
      this.totalNutrients.get(type).find(n => n.nutrient === nutrient.nutrient).amount -= nutrient.amount;
    });
  }

  updateProgress() {
    this.totalNutrients.forEach((amounts, type) =>
      this.nutrientProgressService.setChanges(type, amounts)
    );
  }

  resetProgress() {
    this.totalNutrients.forEach(nutrients =>
      nutrients.forEach(n => n.amount = 0)
    );
    this.updateProgress();
  }

  updateExerciseCalories() {
    let calories = 0;
    this.entries.filter(this.isExercise, this)
      .forEach(n => calories += n.calories);
    this.nutrientProgressService.setExerciseCalories(calories);
  }

  convertToGrams(amount: number, unit: string) {
    return unit === FoodUnit.GRAM ? amount : amount * 28.35;
  }

  isExercise(entry: Entry) {
    return entry.type === EntryType.EXERCISE;
  }

}
