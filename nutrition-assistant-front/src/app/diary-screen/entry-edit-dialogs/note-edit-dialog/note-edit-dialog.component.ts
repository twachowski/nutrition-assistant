import {Component, Inject, OnInit} from '@angular/core';
import {DialogWithToolbarComponent} from '../../../dialog-with-toolbar/dialog-with-toolbar.component';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, Validators} from '@angular/forms';
import {DateService} from 'src/app/services/date.service';
import {EditedNoteEntry} from 'src/app/model/diary/edited-note-entry';
import {DiaryService} from 'src/app/services/diary.service';

export interface NoteEditDialogData {
  content: string;
  position: number;
}

@Component({
  selector: 'app-note-edit-dialog',
  templateUrl: './note-edit-dialog.component.html',
  styleUrls: ['./note-edit-dialog.component.css']
})
export class NoteEditDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private readonly maxContentLength = 255;
  private content = new FormControl(this.data.content, [Validators.required, Validators.maxLength(this.maxContentLength)]);

  private requestInProgress = false;

  constructor(
    private readonly dialogRef: MatDialogRef<NoteEditDialogComponent>,
    private readonly dateService: DateService,
    private readonly diaryService: DiaryService,
    @Inject(MAT_DIALOG_DATA) private data: NoteEditDialogData) {
    super();
    this.content.markAsTouched();
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

  editNote() {
    if (this.data.content === this.content.value) {
      this.dialogRef.close();
    }
    this.requestInProgress = true;
    const date = this.dateService.getCurrentValue();
    const editedEntry: EditedNoteEntry = {
      content: this.content.value
    };
    this.diaryService.editNote(date, this.data.position, editedEntry)
      .subscribe(
        value => {
          this.requestInProgress = false;
          this.dialogRef.close(this.content.value);
        },
        error => {
          this.requestInProgress = false;
          console.log(error);
        }
      );
  }

}
