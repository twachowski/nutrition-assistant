import { Component, OnInit } from '@angular/core';
import { ButtonContainerComponent } from '../button-container/button-container.component';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfigService } from 'src/app/services/dialog-config.service';
import { FoodSearchDialogComponent } from '../../activities/food-search-dialog/food-search-dialog.component';

@Component({
  selector: 'app-dish-button-container',
  templateUrl: './dish-button-container.component.html',
  styleUrls: ['./dish-button-container.component.css']
})
export class DishButtonContainerComponent extends ButtonContainerComponent implements OnInit {

  constructor(
    protected readonly dialog: MatDialog,
    protected readonly dialogConfigService: DialogConfigService) {
    super(dialog, dialogConfigService);
  }

  ngOnInit() {
  }

  openDialog() {
    this.dialog.open(FoodSearchDialogComponent, this.dialogConfigService.getFoodSearchDialogConfig());
  }

}
