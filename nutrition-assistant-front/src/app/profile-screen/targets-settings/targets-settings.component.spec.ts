import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TargetsSettingsComponent } from './targets-settings.component';

describe('TargetsSettingsComponent', () => {
  let component: TargetsSettingsComponent;
  let fixture: ComponentFixture<TargetsSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ TargetsSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TargetsSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
