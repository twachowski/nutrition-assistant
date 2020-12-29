import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FoodDetailsSheetComponent } from './food-details-sheet.component';

describe('FoodDetailsSheetComponent', () => {
  let component: FoodDetailsSheetComponent;
  let fixture: ComponentFixture<FoodDetailsSheetComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodDetailsSheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodDetailsSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
