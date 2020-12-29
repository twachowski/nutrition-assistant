import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UndefinedProgressBarComponent } from './undefined-progress-bar.component';

describe('UndefinedProgressBarComponent', () => {
  let component: UndefinedProgressBarComponent;
  let fixture: ComponentFixture<UndefinedProgressBarComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UndefinedProgressBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UndefinedProgressBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
