import { Component, OnInit, Input } from '@angular/core';
import { NutrientDetail } from 'src/app/model/nutrient-detail';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';

@Component({
  selector: 'app-nutrient-details',
  templateUrl: './nutrient-details.component.html',
  styleUrls: ['./nutrient-details.component.css']
})
export class NutrientDetailsComponent implements OnInit {

  @Input() readonly label: string;
  @Input() private readonly nutrientDetailsType: NutrientDetailsType;
  readonly types = NutrientDetailsType;
  readonly displayedColumns = ['name', 'amount', 'unit', 'progress'];
  amount: number;
  nutrients: NutrientDetail[];

  constructor(protected nutrientDetailsService: NutrientDetailsService) {
  }

  ngOnInit() {
    this.nutrients = this.nutrientDetailsService.get(this.nutrientDetailsType);
  }

  format(value: number) {
    const val = Number(value.toFixed(1));
    return this.natural(val);
  }

  round(value: number) {
    return this.natural(Math.round(value));
  }

  natural(value: number) {
    return value < 0 ? 0 : value;
  }

  getTooltip(nutrient: NutrientDetail) {
    return `${nutrient.name} \n ${nutrient.amount.toFixed(1)} / ${nutrient.target} ${nutrient.unit}`;
  }

}
