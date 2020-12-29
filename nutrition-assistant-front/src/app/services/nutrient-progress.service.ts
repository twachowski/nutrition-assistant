import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { NutrientBasicInfo } from '../model/food/nutrients/nutrient-basic-info';
import { NutrientDetailsType } from '../model/nutrient-details-type.enum';

@Injectable({
  providedIn: 'root'
})
export class NutrientProgressService {

  private readonly changesMap = new Map<NutrientDetailsType, Subject<NutrientBasicInfo[]>>([
    [NutrientDetailsType.GENERAL, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.MACRONUTRIENTS, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.CARBOHYDRATES, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.LIPIDS, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.AMINO_ACIDS, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.VITAMINS, new Subject<NutrientBasicInfo[]>()],
    [NutrientDetailsType.MINERALS, new Subject<NutrientBasicInfo[]>()]
  ]);

  private readonly exerciseCalories = new Subject<number>();

  constructor() { }

  getChanges(type: NutrientDetailsType) {
    return this.changesMap.get(type).asObservable();
  }

  setChanges(type: NutrientDetailsType, changes: NutrientBasicInfo[]) {
    this.changesMap.get(type).next(changes);
  }

  getExerciseCalories() {
    return this.exerciseCalories.asObservable();
  }

  setExerciseCalories(calories: number) {
    this.exerciseCalories.next(calories);
  }

}
