import { PositionChange } from './position-change';

export interface ReorderRequest {

    readonly diaryDate: string;
    readonly positionChange: PositionChange;

}
