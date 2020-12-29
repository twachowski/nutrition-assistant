import { Component, OnInit, ViewChild } from '@angular/core';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { NutrientTargetService } from 'src/app/services/nutrient-target.service';
import { GeneralNutrient } from 'src/app/model/food/nutrients/general-nutrient.enum';

@Component({
  selector: 'app-general-nutrient-details',
  templateUrl: './general-nutrient-details.component.html',
  styleUrls: ['./general-nutrient-details.component.css']
})
export class GeneralNutrientDetailsComponent extends NutrientDetailsComponent implements OnInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService,
    private readonly nutrientTargetService: NutrientTargetService) {
    super(nutrientDetailsService);

    this.nutrientProgressService.getChanges(NutrientDetailsType.GENERAL).subscribe(
      changes => changes.forEach(this.updateAmount, this)
    );
    this.nutrientTargetService.getCalorieTarget().subscribe(
      value => this.nutrientDetailsComponent.nutrients.find(n => n.name === GeneralNutrient.ENERGY).target = value
    );
  }

  ngOnInit() {
  }

  updateAmount(generalNutrient: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === generalNutrient.nutrient)
      .amount = generalNutrient.amount;
  }

}
