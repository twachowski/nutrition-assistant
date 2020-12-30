import {NutrientDetailsType} from '../nutrient-details-type.enum';

export interface HighlightedTarget {

  readonly type: NutrientDetailsType;
  readonly nutrient: string;
  readonly nutrientName: string;

}
