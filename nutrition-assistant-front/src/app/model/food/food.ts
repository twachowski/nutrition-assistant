import {NutrientBasicInfo} from './nutrients/nutrient-basic-info';

export interface Food {

  readonly name: string;
  readonly brand: string;
  readonly nutrients: NutrientBasicInfo[];

}
