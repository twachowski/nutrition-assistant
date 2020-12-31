import {Component, Input, OnInit} from '@angular/core';
import {NutrientProgressService} from '../../../services/nutrient-progress.service';
import {NutrientTargetService} from '../../../services/nutrient-target.service';
import {HighlightedTarget} from '../../../model/diary/highlighted-target';
import {NutrientDetailsType} from '../../../model/nutrient-details-type.enum';
import {Mineral} from '../../../model/food/nutrients/mineral.enum';
import {GeneralNutrient} from '../../../model/food/nutrients/general-nutrient.enum';
import {AminoAcid} from '../../../model/food/nutrients/amino-acid.enum';
import {Carbohydrate} from '../../../model/food/nutrients/carbohydrate.enum';
import {Lipid} from '../../../model/food/nutrients/lipid.enum';
import {Vitamin} from '../../../model/food/nutrients/vitamin.enum';

@Component({
  selector: 'app-elementary-progress',
  templateUrl: './elementary-progress.component.html',
  styleUrls: ['./elementary-progress.component.css']
})
export class ElementaryProgressComponent implements OnInit {

  @Input() private readonly highlightedTarget: HighlightedTarget;
  private progress = 0;
  private target: number;

  constructor(private readonly nutrientProgressService: NutrientProgressService,
              private readonly nutrientTargetService: NutrientTargetService) {
  }

  ngOnInit() {
    this.nutrientProgressService.getChanges(this.highlightedTarget.type).subscribe(
      changes => {
        const amount = changes.find(n => n.nutrient === this.highlightedTarget.nutrientName).amount;
        if (this.target) {
          this.progress = amount * 100 / this.target;
        }
      }
    );
    if (GeneralNutrient[this.highlightedTarget.nutrient] === GeneralNutrient.ENERGY) {
      this.nutrientTargetService.getCalorieTarget().subscribe(
        value => this.target = value
      );
    }
    if (this.highlightedTarget.type === NutrientDetailsType.AMINO_ACIDS) {
      this.target = this.nutrientTargetService.getAminoAcidTarget(AminoAcid[this.highlightedTarget.nutrient]);
    } else if (this.highlightedTarget.type === NutrientDetailsType.CARBOHYDRATES) {
      this.target = this.nutrientTargetService.getCarbohydrateTarget(Carbohydrate[this.highlightedTarget.nutrient]);
    } else if (this.highlightedTarget.type === NutrientDetailsType.GENERAL) {
      this.target = this.nutrientTargetService.getGeneralNutrientTarget(GeneralNutrient[this.highlightedTarget.nutrient]);
    } else if (this.highlightedTarget.type === NutrientDetailsType.LIPIDS) {
      this.target = this.nutrientTargetService.getLipidTarget(Lipid[this.highlightedTarget.nutrient]);
    } else if (this.highlightedTarget.type === NutrientDetailsType.MINERALS) {
      this.target = this.nutrientTargetService.getMineralTarget(Mineral[this.highlightedTarget.nutrient]);
    } else if (this.highlightedTarget.type === NutrientDetailsType.VITAMINS) {
      this.target = this.nutrientTargetService.getVitaminTarget(Vitamin[this.highlightedTarget.nutrient]);
    }
  }

}
