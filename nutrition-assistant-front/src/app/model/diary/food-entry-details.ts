import { FoodUnit } from '../units/food-unit.enum';
import { NutrientBasicInfo } from '../food/nutrients/nutrient-basic-info';

export interface FoodEntryDetails {

    readonly name: string;
    readonly brandName: string;
    readonly amount: number;
    readonly unit: FoodUnit;
    readonly position: number;
    readonly nutrients: NutrientBasicInfo[];

}
