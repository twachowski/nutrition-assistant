import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FoodSearchDialogComponent } from './food-search-dialog.component';

describe('FoodSearchDialogComponent', () => {
  let component: FoodSearchDialogComponent;
  let fixture: ComponentFixture<FoodSearchDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodSearchDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodSearchDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
