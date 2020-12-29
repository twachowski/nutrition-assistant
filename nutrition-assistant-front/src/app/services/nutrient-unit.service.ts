import { Injectable } from '@angular/core';
import { GeneralNutrient } from '../model/food/nutrients/general-nutrient.enum';
import { EnergyUnit } from '../model/units/energy-unit.enum';
import { NutrientUnit } from '../model/units/nutrient-unit.enum';
import { Lipid } from '../model/food/nutrients/lipid.enum';
import { Vitamin } from '../model/food/nutrients/vitamin.enum';
import { Mineral } from '../model/food/nutrients/mineral.enum';

@Injectable({
  providedIn: 'root'
})
export class NutrientUnitService {

  private readonly generalNutrientUnitMap = new Map<GeneralNutrient, string>([
    [GeneralNutrient.ENERGY, EnergyUnit.KCAL],
    [GeneralNutrient.WATER, NutrientUnit.GRAM],
    [GeneralNutrient.CAFFEINE, NutrientUnit.MILLIGRAM],
    [GeneralNutrient.ALCOHOL, NutrientUnit.MILLIGRAM]
  ]);

  private readonly vitaminUnitMap = new Map<Vitamin, string>([
    [Vitamin.A, NutrientUnit.IU],
    [Vitamin.B7, NutrientUnit.MICROGRAM],
    [Vitamin.B12, NutrientUnit.MICROGRAM],
    [Vitamin.D, NutrientUnit.IU],
    [Vitamin.FOLATE, NutrientUnit.MICROGRAM],
    [Vitamin.K, NutrientUnit.MICROGRAM]
  ]);

  constructor() { }

  getGeneralNutrientUnit(nutrient: GeneralNutrient) {
    return this.generalNutrientUnitMap.get(nutrient);
  }

  getCarbohydrateUnit() {
    return NutrientUnit.GRAM;
  }

  getLipidUnit(lipid: Lipid) {
    return lipid === Lipid.CHOLESTEROL ? NutrientUnit.MILLIGRAM : NutrientUnit.GRAM;
  }

  getAminoAcidUnit() {
    return NutrientUnit.GRAM;
  }

  getVitaminUnit(vitamin: Vitamin) {
    const unit = this.vitaminUnitMap.get(vitamin);
    return unit ? unit : NutrientUnit.MILLIGRAM;
  }

  getMineralUnit(mineral: Mineral) {
    return mineral === Mineral.SELENIUM ? NutrientUnit.MICROGRAM : NutrientUnit.MILLIGRAM;
  }

}
