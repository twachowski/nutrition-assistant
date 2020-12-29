import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CarbohydratesDetailsComponent } from './carbohydrates-details.component';

describe('CarbohydratesDetailsComponent', () => {
  let component: CarbohydratesDetailsComponent;
  let fixture: ComponentFixture<CarbohydratesDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CarbohydratesDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarbohydratesDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
