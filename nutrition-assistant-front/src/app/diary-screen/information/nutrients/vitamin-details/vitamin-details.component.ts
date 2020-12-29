import { Component, OnInit, ViewChild } from '@angular/core';
import { NutrientDetailsComponent } from '../nutrient-details/nutrient-details.component';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { NutrientBasicInfo } from 'src/app/model/food/nutrients/nutrient-basic-info';

@Component({
  selector: 'app-vitamin-details',
  templateUrl: './vitamin-details.component.html',
  styleUrls: ['./vitamin-details.component.css']
})
export class VitaminDetailsComponent extends NutrientDetailsComponent implements OnInit {

  @ViewChild(NutrientDetailsComponent)
  private readonly nutrientDetailsComponent;

  constructor(
    protected readonly nutrientDetailsService: NutrientDetailsService,
    private readonly nutrientProgressService: NutrientProgressService) {
    super(nutrientDetailsService);

    this.nutrientProgressService.getChanges(NutrientDetailsType.VITAMINS)
      .subscribe(
        changes => changes.forEach(this.updateAmount, this)
      );
  }

  ngOnInit() {
  }

  updateAmount(vitamin: NutrientBasicInfo) {
    this.nutrientDetailsComponent.nutrients
      .find(nutrient => nutrient.name === vitamin.nutrient)
      .amount = vitamin.amount;
  }

}
