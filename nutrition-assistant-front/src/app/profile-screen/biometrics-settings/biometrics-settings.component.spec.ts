import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BiometricsSettingsComponent } from './biometrics-settings.component';

describe('BiometricsSettingsComponent', () => {
  let component: BiometricsSettingsComponent;
  let fixture: ComponentFixture<BiometricsSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BiometricsSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiometricsSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
