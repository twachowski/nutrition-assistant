import {Injectable} from '@angular/core';
import {NutrientDetail} from '../model/nutrient-detail';
import {NutrientDetailsType} from '../model/nutrient-details-type.enum';
import {NutrientUnitService} from './nutrient-unit.service';
import {NutrientTargetService} from './nutrient-target.service';
import {Vitamin} from '../model/food/nutrients/vitamin.enum';
import {GeneralNutrient} from '../model/food/nutrients/general-nutrient.enum';
import {Lipid} from '../model/food/nutrients/lipid.enum';
import {Mineral} from '../model/food/nutrients/mineral.enum';
import {Carbohydrate} from '../model/food/nutrients/carbohydrate.enum';
import {AminoAcid} from '../model/food/nutrients/amino-acid.enum';
import {Macronutrient} from '../model/food/nutrients/macronutrient.enum';

@Injectable({
  providedIn: 'root'
})
export class NutrientDetailsService {

  private readonly generalNutrients: NutrientDetail[];
  private readonly carbohydrates: NutrientDetail[];
  private readonly lipids: NutrientDetail[];
  private readonly aminoAcids: NutrientDetail[];
  private readonly vitamins: NutrientDetail[];
  private readonly minerals: NutrientDetail[];
  private readonly allNutrients: Map<string, NutrientDetailsType>;

  private map: Map<NutrientDetailsType, NutrientDetail[]>;
  private nameMap = new Map<string, string>();

  constructor(
    private readonly nutrientUnitService: NutrientUnitService,
    private readonly nutrientTargetService: NutrientTargetService) {

    this.generalNutrients = Object.entries(GeneralNutrient).map(
      arr => this.getNutrientDetail(NutrientDetailsType.GENERAL, arr)
    );
    this.carbohydrates = Object.entries(Carbohydrate).map(
      arr => this.getNutrientDetail(NutrientDetailsType.CARBOHYDRATES, arr)
    );
    this.lipids = Object.entries(Lipid).map(
      arr => this.getNutrientDetail(NutrientDetailsType.LIPIDS, arr)
    );
    this.aminoAcids = Object.entries(AminoAcid).map(
      arr => this.getNutrientDetail(NutrientDetailsType.AMINO_ACIDS, arr)
    );
    this.vitamins = Object.entries(Vitamin).map(
      arr => this.getNutrientDetail(NutrientDetailsType.VITAMINS, arr)
    );
    this.minerals = Object.entries(Mineral).map(
      arr => this.getNutrientDetail(NutrientDetailsType.MINERALS, arr)
    );

    this.allNutrients = new Map<string, NutrientDetailsType>();
    this.generalNutrients.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.GENERAL));
    this.carbohydrates.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.CARBOHYDRATES));
    this.lipids.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.LIPIDS));
    this.aminoAcids.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.AMINO_ACIDS));
    this.vitamins.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.VITAMINS));
    this.minerals.forEach(n => this.allNutrients.set(n.name, NutrientDetailsType.MINERALS));

    const carbs = Macronutrient[Macronutrient.TOTAL_CARBS];
    const fat = Macronutrient[Macronutrient.TOTAL_FAT];
    const protein = Macronutrient[Macronutrient.TOTAL_PROTEIN];
    this.allNutrients.set(carbs, NutrientDetailsType.MACRONUTRIENTS);
    this.allNutrients.set(fat, NutrientDetailsType.MACRONUTRIENTS);
    this.allNutrients.set(protein, NutrientDetailsType.MACRONUTRIENTS);

    this.nameMap.set(carbs, carbs);
    this.nameMap.set(fat, fat);
    this.nameMap.set(protein, protein);

    this.map = new Map([
      [NutrientDetailsType.GENERAL, this.generalNutrients],
      [NutrientDetailsType.CARBOHYDRATES, this.carbohydrates],
      [NutrientDetailsType.LIPIDS, this.lipids],
      [NutrientDetailsType.AMINO_ACIDS, this.aminoAcids],
      [NutrientDetailsType.VITAMINS, this.vitamins],
      [NutrientDetailsType.MINERALS, this.minerals]
    ]);
  }

  getNutrientDetail(nutrientType: NutrientDetailsType, arr: [string, any]): NutrientDetail {
    this.nameMap.set(arr[0], arr[1]);
    const unitAndTarget = this.getUnitAndTarget(nutrientType, arr[0]);
    return {
      name: arr[1],
      unit: unitAndTarget[0],
      amount: 0,
      target: unitAndTarget[1]
    };
  }

  getUnitAndTarget(nutrientType: NutrientDetailsType, nutrient: string): [string, number] {
    switch (nutrientType) {
      case NutrientDetailsType.GENERAL:
        return [
          this.nutrientUnitService.getGeneralNutrientUnit(GeneralNutrient[nutrient]),
          this.nutrientTargetService.getGeneralNutrientTarget(GeneralNutrient[nutrient])
        ];
      case NutrientDetailsType.CARBOHYDRATES:
        return [
          this.nutrientUnitService.getCarbohydrateUnit(),
          this.nutrientTargetService.getCarbohydrateTarget(Carbohydrate[nutrient])
        ];
      case NutrientDetailsType.LIPIDS:
        return [
          this.nutrientUnitService.getLipidUnit(Lipid[nutrient]),
          this.nutrientTargetService.getLipidTarget(Lipid[nutrient])
        ];
      case NutrientDetailsType.AMINO_ACIDS:
        return [
          this.nutrientUnitService.getAminoAcidUnit(),
          this.nutrientTargetService.getAminoAcidTarget(AminoAcid[nutrient])
        ];
      case NutrientDetailsType.VITAMINS:
        return [
          this.nutrientUnitService.getVitaminUnit(Vitamin[nutrient]),
          this.nutrientTargetService.getVitaminTarget(Vitamin[nutrient])
        ];
      case NutrientDetailsType.MINERALS:
        return [
          this.nutrientUnitService.getMineralUnit(Mineral[nutrient]),
          this.nutrientTargetService.getMineralTarget(Mineral[nutrient])
        ];
    }
  }

  get(nutrientType: NutrientDetailsType) {
    return this.map.get(nutrientType);
  }

  getNutrientIndex(nutrientType: NutrientDetailsType, nutrientName: string) {
    return this.map.get(nutrientType)
      .findIndex(nutrient => nutrient.name === nutrientName);
  }

  getAllNutrients() {
    return this.allNutrients;
  }

  getNutrientType(nutrient: string) {
    return this.allNutrients.get(nutrient);
  }

  mapNutrientName(name: string) {
    return this.nameMap.get(name);
  }

}
