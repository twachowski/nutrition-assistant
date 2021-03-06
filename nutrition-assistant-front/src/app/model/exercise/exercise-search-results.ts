import { UserSimpleBiometrics } from '../user/user-simple-biometrics';
import { ExerciseDetails } from './exercise-details';

export interface ExerciseSearchResults {

    readonly userSimpleBiometrics: UserSimpleBiometrics;
    readonly exercises: ExerciseDetails[];

}
