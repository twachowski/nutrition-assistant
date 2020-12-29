import { Component, OnInit, ViewChild } from '@angular/core';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';

@Component({
  selector: 'app-mineral-details',
  templateUrl: './mineral-details.component.html',
  styleUrls: ['./mineral-details.component.css']
})
export class MineralDetailsComponent extends NutrientDetailsComponent implements OnInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService) {
    super(nutrientDetailsService);

    this.nutrientProgressService.getChanges(NutrientDetailsType.MINERALS)
      .subscribe(
        changes => changes.forEach(this.updateAmount, this)
      );
  }

  ngOnInit() {
  }

  updateAmount(mineral: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === mineral.nutrient)
      .amount = mineral.amount;
  }

}
