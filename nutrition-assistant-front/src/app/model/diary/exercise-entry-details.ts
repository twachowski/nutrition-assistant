import { ExerciseUnit } from '../units/exercise-unit.enum';

export interface ExerciseEntryDetails {

    readonly name: string;
    readonly duration: number;
    readonly unit: ExerciseUnit;
    readonly calories: number;
    readonly position: number;

}
