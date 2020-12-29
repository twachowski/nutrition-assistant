import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ExerciseButtonContainerComponent } from './exercise-button-container.component';

describe('ExerciseButtonContainerComponent', () => {
  let component: ExerciseButtonContainerComponent;
  let fixture: ComponentFixture<ExerciseButtonContainerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExerciseButtonContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExerciseButtonContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
