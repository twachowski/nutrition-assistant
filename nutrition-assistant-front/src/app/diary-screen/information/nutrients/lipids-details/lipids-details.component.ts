import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { Macronutrient } from 'src/app/model/food/nutrients/macronutrient.enum';

@Component({
  selector: 'app-lipids-details',
  templateUrl: './lipids-details.component.html',
  styleUrls: ['./lipids-details.component.css']
})
export class LipidsDetailsComponent extends NutrientDetailsComponent implements AfterViewInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService) {
    super(nutrientDetailsService);

    this.nutrientProgressService.getChanges(NutrientDetailsType.MACRONUTRIENTS)
      .subscribe(changes => this.updateMacronutrient(changes)
      );

    this.nutrientProgressService.getChanges(NutrientDetailsType.LIPIDS)
      .subscribe(
        changes => changes.forEach(this.updateAmount, this)
      );
  }

  ngAfterViewInit() {
    setTimeout(() => this.nutrientDetailsComponent.amount = 0);
  }

  updateMacronutrient(macros: NutrientBasicInfo[]) {
    const amount = macros.find(macro => macro.nutrient === Macronutrient[Macronutrient.TOTAL_FAT]).amount;
    this.nutrientDetailsComponent.amount = amount;
  }

  updateAmount(lipid: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === lipid.nutrient)
      .amount = lipid.amount;
  }

}
