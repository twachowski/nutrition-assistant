import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NutrientDetailsComponent } from './nutrient-details.component';

describe('NutrientDetailsComponent', () => {
  let component: NutrientDetailsComponent;
  let fixture: ComponentFixture<NutrientDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NutrientDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NutrientDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
