import { NutritionDataProvider } from './nutrition-data-provider.enum';

export interface FoodSearchItem {

    readonly name: string;
    readonly brandName: string;
    readonly externalId: string;
    readonly provider: NutritionDataProvider;

}
