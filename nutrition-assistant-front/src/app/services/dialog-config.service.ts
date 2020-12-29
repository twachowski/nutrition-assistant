import { Injectable } from '@angular/core';
import { MatDialogConfig } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class DialogConfigService {

  private readonly loginDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    width: '400px',
    minHeight: 300
  };

  private readonly registerDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    width: '450px'
  };

  private readonly foodSearchDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    position: { top: '100px' },
    width: '30%',
    minWidth: 600,
    minHeight: 600,
    maxHeight: 600
  };

  private readonly exerciseSearchDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    position: { top: '100px' },
    width: '30%',
    minWidth: 600,
    minHeight: 480,
    maxHeight: 480
  };

  private readonly noteAddDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    position: { top: '100px' },
    width: '30%',
    minWidth: 300
  };

  private readonly entryEditDialogConfig: MatDialogConfig = {
    panelClass: 'dialog-with-toolbar',
    width: '30%',
    minWidth: 300
  };

  constructor() { }

  getLoginDialogConfig() {
    return this.loginDialogConfig;
  }

  getRegisterDialogConfig() {
    return this.registerDialogConfig;
  }

  getFoodSearchDialogConfig() {
    return this.foodSearchDialogConfig;
  }

  getExerciseSearchDialogConfig() {
    return this.exerciseSearchDialogConfig;
  }

  getNoteAddDialogConfig() {
    return this.noteAddDialogConfig;
  }

  getEntryEditDialogConfig(data: any) {
    this.entryEditDialogConfig.data = data;
    return this.entryEditDialogConfig;
  }

}
