import {NutritionDataProvider} from './nutrition-data-provider.enum';

export interface FoodSearchItem {

  readonly id: string;
  readonly provider: NutritionDataProvider;
  readonly name: string;
  readonly brand: string;

}
