import {FoodUnit} from '../units/food-unit.enum';
import {NutrientBasicInfo} from '../food/nutrients/nutrient-basic-info';

export interface FoodEntryDetails {

  readonly name: string;
  readonly brand: string;
  readonly massUnit: FoodUnit;
  readonly amount: number;
  readonly position: number;
  readonly nutrients: NutrientBasicInfo[];

}
