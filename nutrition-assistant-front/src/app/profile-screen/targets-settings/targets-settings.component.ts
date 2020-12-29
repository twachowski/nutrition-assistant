import { Component, OnInit } from '@angular/core';
import { NutrientDetailsService } from 'src/app/services/nutrient-details.service';
import { NutrientDetailsType } from 'src/app/model/nutrient-details-type.enum';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-targets-settings',
  templateUrl: './targets-settings.component.html',
  styleUrls: ['./targets-settings.component.css']
})
export class TargetsSettingsComponent implements OnInit {

  private readonly formControls: FormControl[][] = [];
  private readonly nutrientTypes = [
    [NutrientDetailsType.GENERAL, 'General'],
    [NutrientDetailsType.CARBOHYDRATES, 'Carbohydrate'],
    [NutrientDetailsType.LIPIDS, 'Lipid'],
    [NutrientDetailsType.AMINO_ACIDS, 'Amino acid'],
    [NutrientDetailsType.VITAMINS, 'Vitamin'],
    [NutrientDetailsType.MINERALS, 'Mineral']
  ];

  constructor(private readonly nutrientDetailsService: NutrientDetailsService) {
    for (let i = 0; i < 6; ++i) {
      this.formControls.push([new FormControl(0), new FormControl(0)]);
    }
    this.formControls.forEach(controls => {
      controls[0].valueChanges.subscribe(val => controls[1].setValue(0));
    });
  }

  ngOnInit() {
  }

  getNutrients(type: NutrientDetailsType) {
    return this.nutrientDetailsService.get(type);
  }

}
