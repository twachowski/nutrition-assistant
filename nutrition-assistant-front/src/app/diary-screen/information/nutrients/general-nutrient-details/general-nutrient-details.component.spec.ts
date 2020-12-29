import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { GeneralNutrientDetailsComponent } from './general-nutrient-details.component';

describe('GeneralNutrientDetailsComponent', () => {
  let component: GeneralNutrientDetailsComponent;
  let fixture: ComponentFixture<GeneralNutrientDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneralNutrientDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneralNutrientDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
