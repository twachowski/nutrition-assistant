import {Component, OnInit} from '@angular/core';
import {DialogWithToolbarComponent} from 'src/app/dialog-with-toolbar/dialog-with-toolbar.component';
import {FormControl, Validators} from '@angular/forms';
import {MatDialogRef} from '@angular/material/dialog';
import {DiaryService} from 'src/app/services/diary.service';
import {DateService} from 'src/app/services/date.service';
import {EntryService} from 'src/app/services/entry.service';
import {NewNoteEntry} from 'src/app/model/diary/new-note-entry';

@Component({
  selector: 'app-note-add-dialog',
  templateUrl: './note-add-dialog.component.html',
  styleUrls: ['./note-add-dialog.component.css']
})
export class NoteAddDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private readonly maxContentLength = 255;
  content = new FormControl('', [Validators.required, Validators.maxLength(this.maxContentLength)]);

  constructor(
    private readonly dialogRef: MatDialogRef<NoteAddDialogComponent>,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    private readonly entryService: EntryService) {
    super();
  }

  ngOnInit() {
  }

  getErrorMessage() {
    if (this.content.hasError('required')) {
      return 'Note must not be empty';
    } else if (this.content.hasError('maxlength')) {
      return `Note content should not exceed ${this.maxContentLength} characters`;
    } else {
      return '';
    }
  }

  addNewNote() {
    const date = this.dateService.getCurrentValue();
    const newNoteEntry: NewNoteEntry = {
      content: this.content.value
    };
    this.diaryService.addNoteEntry(date, newNoteEntry)
      .subscribe(
        value => {
          this.entryService.setNoteEntry(this.content.value);
          this.dialogRef.close(this.content.value);
        },
        error => console.log(error)
      );
  }

}
