import { Component, OnInit } from '@angular/core';
import { ButtonContainerComponent } from '../button-container/button-container.component';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfigService } from 'src/app/services/dialog-config.service';
import { ExerciseSearchDialogComponent } from '../../activities/exercise-search-dialog/exercise-search-dialog.component';

@Component({
  selector: 'app-exercise-button-container',
  templateUrl: './exercise-button-container.component.html',
  styleUrls: ['./exercise-button-container.component.css']
})
export class ExerciseButtonContainerComponent extends ButtonContainerComponent implements OnInit {

  constructor(
    protected readonly dialog: MatDialog,
    protected readonly dialogConfigService: DialogConfigService) {
    super(dialog, dialogConfigService);
  }

  ngOnInit() {
  }

  openDialog() {
    this.dialog.open(ExerciseSearchDialogComponent, this.dialogConfigService.getExerciseSearchDialogConfig());
  }

}
