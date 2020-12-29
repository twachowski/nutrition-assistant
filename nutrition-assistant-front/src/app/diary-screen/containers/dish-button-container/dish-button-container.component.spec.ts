import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DishButtonContainerComponent } from './dish-button-container.component';

describe('DishButtonContainerComponent', () => {
  let component: DishButtonContainerComponent;
  let fixture: ComponentFixture<DishButtonContainerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DishButtonContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DishButtonContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
