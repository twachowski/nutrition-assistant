import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { GeneralProgressComponent } from './general-progress.component';

describe('GeneralProgressComponent', () => {
  let component: GeneralProgressComponent;
  let fixture: ComponentFixture<GeneralProgressComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneralProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneralProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
