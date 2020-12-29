import { Entry } from './entry';
import { NoteIcon } from './icon/note-icon';
import { EntryType } from './entry-type.enum';

export class NoteEntry implements Entry {

    readonly icon = new NoteIcon();
    readonly type = EntryType.NOTE;
    readonly unit = null;
    readonly amount = null;
    readonly calories = null;

    constructor(public name: string) { }

}
