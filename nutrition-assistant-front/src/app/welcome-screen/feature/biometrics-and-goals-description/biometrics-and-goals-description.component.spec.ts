import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BiometricsAndGoalsDescriptionComponent } from './biometrics-and-goals-description.component';

describe('BiometricsAndGoalsDescriptionComponent', () => {
  let component: BiometricsAndGoalsDescriptionComponent;
  let fixture: ComponentFixture<BiometricsAndGoalsDescriptionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricsAndGoalsDescriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricsAndGoalsDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
