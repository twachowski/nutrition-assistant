import { Icon } from './icon/icon';
import { EntryType } from './entry-type.enum';

export interface Entry {

    readonly icon: Icon;
    readonly type: EntryType;
    name: string;
    unit: string;
    amount: number;
    calories: number;

}
