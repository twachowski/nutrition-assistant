import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ErrorSnackBarComponent } from './error-snack-bar.component';

describe('ErrorSnackBarComponent', () => {
  let component: ErrorSnackBarComponent;
  let fixture: ComponentFixture<ErrorSnackBarComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ErrorSnackBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorSnackBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
