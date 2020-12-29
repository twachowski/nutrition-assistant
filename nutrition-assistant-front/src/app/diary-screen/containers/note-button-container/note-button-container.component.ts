import { Component, OnInit } from '@angular/core';
import { ButtonContainerComponent } from '../button-container/button-container.component';
import { DialogConfigService } from 'src/app/services/dialog-config.service';
import { MatDialog } from '@angular/material/dialog';
import { NoteAddDialogComponent } from '../../activities/note-add-dialog/note-add-dialog.component';

@Component({
  selector: 'app-note-button-container',
  templateUrl: './note-button-container.component.html',
  styleUrls: ['./note-button-container.component.css']
})
export class NoteButtonContainerComponent extends ButtonContainerComponent implements OnInit {

  constructor(
    protected readonly dialog: MatDialog,
    protected readonly dialogConfigService: DialogConfigService) {
    super(dialog, dialogConfigService);
  }

  ngOnInit() {
  }

  openDialog() {
    this.dialog.open(NoteAddDialogComponent, this.dialogConfigService.getNoteAddDialogConfig());
  }

}
