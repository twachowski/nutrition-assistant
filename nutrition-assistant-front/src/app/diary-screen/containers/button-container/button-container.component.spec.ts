import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ButtonContainerComponent } from './button-container.component';

describe('ButtonContainerComponent', () => {
  let component: ButtonContainerComponent;
  let fixture: ComponentFixture<ButtonContainerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ButtonContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ButtonContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
