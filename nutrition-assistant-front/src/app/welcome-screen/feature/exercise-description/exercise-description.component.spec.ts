import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ExerciseDescriptionComponent } from './exercise-description.component';

describe('ExerciseDescriptionComponent', () => {
  let component: ExerciseDescriptionComponent;
  let fixture: ComponentFixture<ExerciseDescriptionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExerciseDescriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExerciseDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
