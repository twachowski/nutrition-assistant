import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { Macronutrient } from 'src/app/model/food/nutrients/macronutrient.enum';

@Component({
  selector: 'app-carbohydrates-details',
  templateUrl: './carbohydrates-details.component.html',
  styleUrls: ['./carbohydrates-details.component.css']
})
export class CarbohydratesDetailsComponent extends NutrientDetailsComponent implements AfterViewInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService) {
    super(nutrientDetailsService);

    this.nutrientProgressService.getChanges(NutrientDetailsType.MACRONUTRIENTS)
      .subscribe(changes => this.updateMacronutrient(changes)
      );
    this.nutrientProgressService.getChanges(NutrientDetailsType.CARBOHYDRATES)
      .subscribe(
        changes => changes.forEach(this.updateMicronutrients, this)
      );
  }

  ngAfterViewInit() {
    setTimeout(() => this.nutrientDetailsComponent.amount = 0);
  }

  updateMacronutrient(macros: NutrientBasicInfo[]) {
    const amount = macros.find(macro => macro.nutrient === Macronutrient[Macronutrient.TOTAL_CARBS]).amount;
    this.nutrientDetailsComponent.amount = amount;
  }

  updateMicronutrients(carb: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === carb.nutrient)
      .amount = carb.amount;
  }

}
