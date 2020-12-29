import { Component, OnInit } from '@angular/core';
import { DialogWithToolbarComponent } from 'src/app/dialog-with-toolbar/dialog-with-toolbar.component';
import { FormControl, Validators } from '@angular/forms';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { MatDialogRef } from '@angular/material/dialog';
import { FoodSearchItem } from 'src/app/model/food/food-search-item';
import { NutritionDataProvider } from 'src/app/model/food/nutrition-data-provider.enum';
import { FoodService } from 'src/app/services/food.service';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { FoodDetailsSheetComponent, FoodDetailsSheetData } from './food-details-sheet/food-details-sheet.component';
import { EntryService } from 'src/app/services/entry.service';

export interface NewFoodDialogData {
  readonly name: string;
  amount: number;
  unit: string;
  calories: number;
  nutrients: NutrientBasicInfo[];
}

@Component({
  selector: 'app-food-search-dialog',
  templateUrl: './food-search-dialog.component.html',
  styleUrls: ['./food-search-dialog.component.css']
})
export class FoodSearchDialogComponent extends DialogWithToolbarComponent implements OnInit {

  private foodName = new FormControl('', [Validators.required]);
  private foods: FoodSearchItem[];
  private foodsDetails: NutrientBasicInfo[][];
  private readonly displayedColumns = ['name', 'provider'];
  private requestInProgress = false;

  private selectedFood: FoodSearchItem;
  private selectedFoodIndex: number;

  constructor(
    private readonly dialogRef: MatDialogRef<FoodSearchDialogComponent>,
    private readonly foodService: FoodService,
    private readonly entryService: EntryService,
    private readonly foodDetailsSheet: MatBottomSheet) {
    super();
  }

  ngOnInit() {
  }

  search() {
    this.requestInProgress = true;
    this.foodService.search(this.foodName.value)
      .subscribe(
        data => {
          this.foods = data.foods;
          this.foodsDetails = new Array<NutrientBasicInfo[]>(data.foods.length);
          this.requestInProgress = false;
        },
        error => {
          this.foods = [];
          this.foodsDetails = [];
          this.requestInProgress = false;
        }
      );
  }

  getProvider(provider: string) {
    return NutritionDataProvider[provider];
  }

  onRowClick(food: FoodSearchItem, index: number) {
    if (this.selectedFood === food) {
      this.selectedFood = null;
    } else {
      this.selectedFood = food;
      this.selectedFoodIndex = index;
      if (!this.foodsDetails[index]) {
        this.requestInProgress = true;
        this.foodService.getDetails(this.selectedFood).subscribe(
          data => {
            this.requestInProgress = false;
            this.foodsDetails[index] = data.food.nutrients;
            this.openFoodDetailsSheet();
          },
          error => {
            this.requestInProgress = false;
            console.log(error);
          }
        );
      } else {
        this.openFoodDetailsSheet();
      }
    }
  }

  openFoodDetailsSheet() {
    const sheetData: FoodDetailsSheetData = {
      externalId: this.selectedFood.id,
      provider: this.selectedFood.provider,
      nutrients: this.foodsDetails[this.selectedFoodIndex]
    };
    const sheet = this.foodDetailsSheet.open(FoodDetailsSheetComponent, { data: sheetData });
    sheet.afterDismissed().subscribe(result => {
      if (result) {
        const newFoodData: NewFoodDialogData = {
          name: this.getSelectedFoodName(),
          amount: result.amount,
          unit: result.unit,
          calories: result.calories,
          nutrients: this.foodsDetails[this.selectedFoodIndex]
        };
        this.entryService.setFoodEntry(newFoodData);
        this.dialogRef.close();
      }
    });
  }

  getSelectedFoodName() {
    const foodName = this.selectedFood.name;
    const brandName = this.selectedFood.brand;
    return brandName ? foodName.concat(', ', brandName) : foodName;
  }

}
