import { NutritionDataProvider } from '../../food/nutrition-data-provider.enum';
import { FoodUnit } from '../../units/food-unit.enum';

export interface NewFoodEntry {

    readonly externalId: string;
    readonly provider: NutritionDataProvider;
    readonly amount: number;
    readonly unit: string;
    readonly position: number;

}
