import { Entry } from './entry';
import { ExerciseIcon } from './icon/exercise-icon';
import { EntryType } from './entry-type.enum';

export class ExerciseEntry implements Entry {

    readonly icon = new ExerciseIcon();
    readonly type = EntryType.EXERCISE;

    constructor(
        public readonly name: string,
        public unit: string,
        public amount: number,
        public calories: number) { }

}
