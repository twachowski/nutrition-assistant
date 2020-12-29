import {ExerciseUnit} from '../units/exercise-unit.enum';

export interface ExerciseEntryDetails {

  readonly name: string;
  readonly timeUnit: ExerciseUnit;
  readonly duration: number;
  readonly calories: number;
  readonly position: number;

}
