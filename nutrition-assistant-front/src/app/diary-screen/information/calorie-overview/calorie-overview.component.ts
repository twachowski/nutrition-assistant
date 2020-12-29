import { Component, OnInit } from '@angular/core';
import { NutrientTargetService } from 'src/app/services/nutrient-target.service';
import { NutrientProgressService } from 'src/app/services/nutrient-progress.service';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { GeneralNutrient } from 'src/app/model/food/nutrients/general-nutrient.enum';

@Component({
  selector: 'app-calorie-overview',
  templateUrl: './calorie-overview.component.html',
  styleUrls: ['./calorie-overview.component.css']
})
export class CalorieOverviewComponent implements OnInit {

  private calorieTarget = 0;
  private foodCalories = 0;
  private exerciseCalories = 0;

  constructor(
    private readonly nutrientTargetService: NutrientTargetService,
    private readonly nutrientProgressService: NutrientProgressService) {
    this.nutrientTargetService.getCalorieTarget().subscribe(
      value => this.calorieTarget = value
    );
    this.nutrientProgressService.getChanges(NutrientDetailsType.GENERAL).subscribe(
      changes => this.foodCalories = this.formatValue(changes.find(n => n.nutrient === GeneralNutrient.ENERGY).amount)
    );
    this.nutrientProgressService.getExerciseCalories().subscribe(
      value => this.exerciseCalories = -this.formatValue(value)
    );
  }

  ngOnInit() {
  }

  formatValue(value: number) {
    return Number(value.toFixed(1));
  }

}
