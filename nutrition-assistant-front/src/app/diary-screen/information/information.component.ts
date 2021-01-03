import {Component, OnDestroy, OnInit} from '@angular/core';
import {NutrientTargetService} from 'src/app/services/nutrient-target.service';
import {NutrientProgressService} from 'src/app/services/nutrient-progress.service';
import {NutrientDetailsType} from 'src/app/model/nutrient-details-type.enum';
import {GeneralNutrient} from 'src/app/model/food/nutrients/general-nutrient.enum';
import {NutrientDetailsService} from 'src/app/services/nutrient-details.service';
import {Subscription} from 'rxjs';
import {UserService} from '../../services/user.service';
import {AminoAcid} from '../../model/food/nutrients/amino-acid.enum';
import {Carbohydrate} from '../../model/food/nutrients/carbohydrate.enum';
import {Lipid} from '../../model/food/nutrients/lipid.enum';
import {Mineral} from '../../model/food/nutrients/mineral.enum';
import {Vitamin} from '../../model/food/nutrients/vitamin.enum';
import {HighlightedTarget} from '../../model/diary/highlighted-target';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrls: ['./information.component.css']
})
export class InformationComponent implements OnInit, OnDestroy {

  overallProgress = 0;
  private readonly progressMap = new Map<NutrientDetailsType, number>();
  private readonly progressCoeff;

  highlightedTarget1: HighlightedTarget;
  highlightedTarget2: HighlightedTarget;
  highlightedTarget3: HighlightedTarget;
  highlightedTarget4: HighlightedTarget;
  highlightedTarget5: HighlightedTarget;
  highlightedTarget6: HighlightedTarget;

  calorieTarget = 0;
  calorieAmount = 0;

  private readonly subscription: Subscription;

  constructor(
    private readonly nutrientTargetService: NutrientTargetService,
    private readonly nutrientProgressService: NutrientProgressService,
    private readonly nutrientDetailsService: NutrientDetailsService,
    private readonly userService: UserService) {
    userService.getUserHighlightedTargets()
      .subscribe(
        data => {
          this.highlightedTarget1 = this.getTarget(data.target1);
          this.highlightedTarget2 = this.getTarget(data.target2);
          this.highlightedTarget3 = this.getTarget(data.target3);
          this.highlightedTarget4 = this.getTarget(data.target4);
          this.highlightedTarget5 = this.getTarget(data.target5);
          this.highlightedTarget6 = this.getTarget(data.target6);
        },
        error => {
          console.log(error);
        }
      );
    this.subscription = this.nutrientTargetService.getCalorieTarget().subscribe(
      value => this.calorieTarget = value
    );
    const progressSub = this.nutrientProgressService.getChanges(NutrientDetailsType.GENERAL).subscribe(
      changes => this.calorieAmount = changes.find(n => n.nutrient === GeneralNutrient.ENERGY).amount
    );
    this.subscription.add(progressSub);

    const nutrientTypes = [
      NutrientDetailsType.GENERAL,
      NutrientDetailsType.CARBOHYDRATES,
      NutrientDetailsType.LIPIDS,
      NutrientDetailsType.AMINO_ACIDS,
      NutrientDetailsType.VITAMINS,
      NutrientDetailsType.MINERALS
    ];
    let nutrientsWithTargetCount = 0;
    nutrientTypes.forEach(type => {
      this.progressMap.set(type, 0);
      const nutrientDetails = this.nutrientDetailsService.get(type);
      nutrientDetails.forEach(nutrient => {
        if (nutrient.target) {
          nutrientsWithTargetCount++;
        }
      });
      const sub = this.nutrientProgressService.getChanges(type).subscribe(
        changes => {
          let progress = 0;
          changes.forEach(nutrient => {
            const nutrientDetail = nutrientDetails.find(n => n.name === nutrient.nutrient);
            if (nutrientDetail.target) {
              const nutrientProgress = nutrient.amount / nutrientDetail.target;
              progress += nutrientProgress > 1 ? 1 : nutrientProgress;
            }
          });
          this.progressMap.set(type, progress);
          this.calculateProgress();
        }
      );
      this.subscription.add(sub);
    });
    this.progressCoeff = 100 / nutrientsWithTargetCount;
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  calculateProgress() {
    let progress = 0;
    this.progressMap.forEach(value => progress += value);
    this.overallProgress = progress * this.progressCoeff;
  }

  getTarget(nutrient: string): HighlightedTarget {
    let name: string;
    let type: NutrientDetailsType;
    if (AminoAcid[nutrient]) {
      type = NutrientDetailsType.AMINO_ACIDS;
      name = AminoAcid[nutrient];
    } else if (Carbohydrate[nutrient]) {
      type = NutrientDetailsType.CARBOHYDRATES;
      name = Carbohydrate[nutrient];
    } else if (GeneralNutrient[nutrient]) {
      type = NutrientDetailsType.GENERAL;
      name = GeneralNutrient[nutrient];
    } else if (Lipid[nutrient]) {
      type = NutrientDetailsType.LIPIDS;
      name = Lipid[nutrient];
    } else if (Mineral[nutrient]) {
      type = NutrientDetailsType.MINERALS;
      name = Mineral[nutrient];
    } else if (Vitamin[nutrient]) {
      type = NutrientDetailsType.VITAMINS;
      name = Vitamin[nutrient];
    }
    return {
      type,
      nutrient,
      nutrientName: name
    };
  }

}
