import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { FoodsDescriptionComponent } from './foods-description.component';

describe('FoodsDescriptionComponent', () => {
  let component: FoodsDescriptionComponent;
  let fixture: ComponentFixture<FoodsDescriptionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodsDescriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodsDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
