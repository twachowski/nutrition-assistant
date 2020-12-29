import { Entry } from './entry';
import { FoodIcon } from './icon/food-icon';
import { EntryType } from './entry-type.enum';
import { NutrientBasicInfo } from '../food/nutrients/nutrient-basic-info';

export class FoodEntry implements Entry {

    readonly icon = new FoodIcon();
    readonly type = EntryType.FOOD;

    constructor(
        public readonly name: string,
        public unit: string,
        public amount: number,
        public calories: number,
        public readonly nutrients: NutrientBasicInfo[]) { }

}
