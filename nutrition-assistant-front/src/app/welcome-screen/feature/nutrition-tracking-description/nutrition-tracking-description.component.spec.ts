import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NutritionTrackingDescriptionComponent } from './nutrition-tracking-description.component';

describe('NutritionTrackingDescriptionComponent', () => {
  let component: NutritionTrackingDescriptionComponent;
  let fixture: ComponentFixture<NutritionTrackingDescriptionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NutritionTrackingDescriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NutritionTrackingDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
