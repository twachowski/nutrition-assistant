import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { Macronutrient } from 'src/app/model/food/nutrients/macronutrient.enum';

@Component({
  selector: 'app-protein-details',
  templateUrl: './protein-details.component.html',
  styleUrls: ['./protein-details.component.css']
})
export class ProteinDetailsComponent extends NutrientDetailsComponent implements AfterViewInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService) {
    super(nutrientDetailsService);


    this.nutrientProgressService.getChanges(NutrientDetailsType.MACRONUTRIENTS)
      .subscribe(changes => this.updateMacronutrient(changes)
      );
    this.nutrientProgressService.getChanges(NutrientDetailsType.AMINO_ACIDS)
      .subscribe(
        changes => changes.forEach(this.updateAmount, this)
      );
  }

  ngAfterViewInit() {
    setTimeout(() => this.nutrientDetailsComponent.amount = 0);
  }

  updateMacronutrient(macros: NutrientBasicInfo[]) {
    const amount = macros.find(macro => macro.nutrient === Macronutrient[Macronutrient.TOTAL_PROTEIN]).amount;
    this.nutrientDetailsComponent.amount = amount;
  }

  updateAmount(aminoAcid: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === aminoAcid.nutrient)
      .amount = aminoAcid.amount;
  }

}
