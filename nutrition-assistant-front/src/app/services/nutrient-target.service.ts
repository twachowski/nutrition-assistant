import { Injectable } from '@angular/core';
import { GeneralNutrient } from '../model/food/nutrients/general-nutrient.enum';
import { Carbohydrate } from '../model/food/nutrients/carbohydrate.enum';
import { Lipid } from '../model/food/nutrients/lipid.enum';
import { AminoAcid } from '../model/food/nutrients/amino-acid.enum';
import { Vitamin } from '../model/food/nutrients/vitamin.enum';
import { Mineral } from '../model/food/nutrients/mineral.enum';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NutrientTargetService {

  private readonly aminoAcidTargetMap = new Map<AminoAcid, number>([
    [AminoAcid.CYSTEINE, 0.76],
    [AminoAcid.HISTIDINE, 1.12],
    [AminoAcid.ISOLEUCINE, 1.52],
    [AminoAcid.LEUCINE, 3.36],
    [AminoAcid.LYSINE, 3.04],
    [AminoAcid.METHIONINE, 0.76],
    [AminoAcid.PHENYLALANINE, 1.32],
    [AminoAcid.THREONINE, 1.6],
    [AminoAcid.TRYPTOPHAN, 0.4],
    [AminoAcid.TYROSINE, 1.32],
    [AminoAcid.VALINE, 1.92]
  ]);

  private readonly vitaminTargetMap = new Map<Vitamin, number>([
    [Vitamin.A, 3000],
    [Vitamin.B1, 1.2],
    [Vitamin.B2, 1.3],
    [Vitamin.B3, 16],
    [Vitamin.B4, 550],
    [Vitamin.B5, 5],
    [Vitamin.B6, 1.3],
    [Vitamin.B7, 30],
    [Vitamin.B12, 2.4],
    [Vitamin.C, 90],
    [Vitamin.D, 600],
    [Vitamin.E, 15],
    [Vitamin.FOLATE, 400],
    [Vitamin.K, 120]
  ]);

  private readonly mineralTargetMap = new Map<Mineral, number>([
    [Mineral.CALCIUM, 1000],
    [Mineral.COPPER, 0.9],
    [Mineral.FLUORIDE, 4],
    [Mineral.IRON, 8],
    [Mineral.MAGNESIUM, 400],
    [Mineral.MANGANESE, 2.3],
    [Mineral.PHOSPHORUS, 700],
    [Mineral.POTASSIUM, 3400],
    [Mineral.SELENIUM, 55],
    [Mineral.SODIUM, 1500],
    [Mineral.ZINC, 11]
  ]);

  private readonly calorieTarget = new Subject<number>();

  constructor() { }

  getGeneralNutrientTarget(nutrient: GeneralNutrient) {
    return nutrient === GeneralNutrient.WATER ? 3700 : 0;
  }

  getCarbohydrateTarget(carb: Carbohydrate) {
    return carb === Carbohydrate.FIBER ? 38 : 0;
  }

  getLipidTarget(lipid: Lipid) {
    if (lipid === Lipid.OMEGA3) {
      return 1.6;
    } else if (lipid === Lipid.OMEGA6) {
      return 17;
    } else {
      return 0;
    }
  }

  getAminoAcidTarget(aminoAcid: AminoAcid) {
    return this.aminoAcidTargetMap.get(aminoAcid);
  }

  getVitaminTarget(vitamin: Vitamin) {
    return this.vitaminTargetMap.get(vitamin);
  }

  getMineralTarget(mineral: Mineral) {
    return this.mineralTargetMap.get(mineral);
  }

  getCalorieTarget() {
    return this.calorieTarget.asObservable();
  }

  setCalorieTarget(target: number) {
    this.calorieTarget.next(Math.round(target));
  }

}
