import {Component, OnInit} from '@angular/core';
import {NutrientDetailsService} from 'src/app/services/nutrient-details.service';
import {NutrientDetailsType} from 'src/app/model/nutrient-details-type.enum';
import {FormControl} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {AminoAcid} from '../../model/food/nutrients/amino-acid.enum';
import {Carbohydrate} from '../../model/food/nutrients/carbohydrate.enum';
import {GeneralNutrient} from '../../model/food/nutrients/general-nutrient.enum';
import {Lipid} from '../../model/food/nutrients/lipid.enum';
import {Mineral} from '../../model/food/nutrients/mineral.enum';
import {Vitamin} from '../../model/food/nutrients/vitamin.enum';
import {HighlightedTargetsRequest} from '../../model/user/highlighted-targets-request';

@Component({
  selector: 'app-targets-settings',
  templateUrl: './targets-settings.component.html',
  styleUrls: ['./targets-settings.component.css']
})
export class TargetsSettingsComponent implements OnInit {

  private requestInProgress = false;
  private readonly formControls: FormControl[][] = [];
  private readonly nutrientTypes = [
    [NutrientDetailsType.GENERAL, 'General'],
    [NutrientDetailsType.CARBOHYDRATES, 'Carbohydrate'],
    [NutrientDetailsType.LIPIDS, 'Lipid'],
    [NutrientDetailsType.AMINO_ACIDS, 'Amino acid'],
    [NutrientDetailsType.VITAMINS, 'Vitamin'],
    [NutrientDetailsType.MINERALS, 'Mineral']
  ];

  constructor(private readonly nutrientDetailsService: NutrientDetailsService,
              private readonly userService: UserService) {
    for (let i = 0; i < 6; ++i) {
      this.formControls.push([new FormControl(0), new FormControl(0)]);
    }
    this.formControls.forEach(controls => {
      controls[0].valueChanges.subscribe(val => controls[1].setValue(0));
    });
    this.requestInProgress = true;
    this.userService.getUserHighlightedTargets().subscribe(
      data => {
        this.setFormValue(this.formControls[0], data.target1);
        this.setFormValue(this.formControls[1], data.target2);
        this.setFormValue(this.formControls[2], data.target3);
        this.setFormValue(this.formControls[3], data.target4);
        this.setFormValue(this.formControls[4], data.target5);
        this.setFormValue(this.formControls[5], data.target6);
        this.requestInProgress = false;
      },
      error => {
        console.log(error);
        this.requestInProgress = false;
      }
    );
  }

  ngOnInit() {
  }

  getNutrients(type: NutrientDetailsType) {
    return this.nutrientDetailsService.get(type);
  }

  setFormValue(formControl: FormControl[], nutrientName: string) {
    let type: NutrientDetailsType;
    let name: string;
    if (AminoAcid[nutrientName]) {
      type = NutrientDetailsType.AMINO_ACIDS;
      name = AminoAcid[nutrientName];
    } else if (Carbohydrate[nutrientName]) {
      type = NutrientDetailsType.CARBOHYDRATES;
      name = Carbohydrate[nutrientName];
    } else if (GeneralNutrient[nutrientName]) {
      type = NutrientDetailsType.GENERAL;
      name = GeneralNutrient[nutrientName];
    } else if (Lipid[nutrientName]) {
      type = NutrientDetailsType.LIPIDS;
      name = Lipid[nutrientName];
    } else if (Mineral[nutrientName]) {
      type = NutrientDetailsType.MINERALS;
      name = Mineral[nutrientName];
    } else if (Vitamin[nutrientName]) {
      type = NutrientDetailsType.VITAMINS;
      name = Vitamin[nutrientName];
    }
    formControl[0].setValue(type);
    formControl[1].setValue(this.nutrientDetailsService.getNutrientIndex(type, name));
  }

  saveTargets() {
    this.requestInProgress = true;
    const targets: HighlightedTargetsRequest = {
      target1: this.getNutrientName(this.formControls[0]),
      target2: this.getNutrientName(this.formControls[1]),
      target3: this.getNutrientName(this.formControls[2]),
      target4: this.getNutrientName(this.formControls[3]),
      target5: this.getNutrientName(this.formControls[4]),
      target6: this.getNutrientName(this.formControls[5])
    };
    this.userService.saveUserHighlightedTargets(targets)
      .subscribe(
        data => {
          this.requestInProgress = false;
        },
        error => {
          console.log(error);
          this.requestInProgress = false;
        }
      );
  }

  private getNutrientName(formControl: FormControl[]) {
    const type: NutrientDetailsType = formControl[0].value;
    const name = this.nutrientDetailsService.get(formControl[0].value)[formControl[1].value].name;
    let nutrientType;
    if (type === NutrientDetailsType.AMINO_ACIDS) {
      nutrientType = AminoAcid;
    } else if (type === NutrientDetailsType.CARBOHYDRATES) {
      nutrientType = Carbohydrate;
    } else if (type === NutrientDetailsType.GENERAL) {
      nutrientType = GeneralNutrient;
    } else if (type === NutrientDetailsType.LIPIDS) {
      nutrientType = Lipid;
    } else if (type === NutrientDetailsType.MINERALS) {
      nutrientType = Mineral;
    } else if (type === NutrientDetailsType.VITAMINS) {
      nutrientType = Vitamin;
    }
    return Object.entries(nutrientType)
      .find(entry => entry[1] === name)[0];
  }

}
