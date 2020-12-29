import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CalorieOverviewComponent } from './calorie-overview.component';

describe('CalorieOverviewComponent', () => {
  let component: CalorieOverviewComponent;
  let fixture: ComponentFixture<CalorieOverviewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CalorieOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalorieOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
