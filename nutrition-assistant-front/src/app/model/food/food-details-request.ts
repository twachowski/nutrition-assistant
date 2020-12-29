import { NutritionDataProvider } from './nutrition-data-provider.enum';

export interface FoodDetailsRequest {

    readonly externalId: string;
    readonly provider: NutritionDataProvider;

}
