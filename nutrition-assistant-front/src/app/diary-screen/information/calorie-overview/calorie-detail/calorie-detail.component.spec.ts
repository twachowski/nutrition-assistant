import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CalorieDetailComponent } from './calorie-detail.component';

describe('CalorieDetailComponent', () => {
  let component: CalorieDetailComponent;
  let fixture: ComponentFixture<CalorieDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CalorieDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalorieDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
