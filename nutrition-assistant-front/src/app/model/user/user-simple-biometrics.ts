import { Sex } from './sex.enum';

export interface UserSimpleBiometrics {

    readonly age: number;
    readonly sex: Sex;
    readonly height: number;
    readonly weight: number;

}
