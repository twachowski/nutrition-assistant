import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ExerciseSearchDialogComponent } from './exercise-search-dialog.component';

describe('ExerciseSearchDialogComponent', () => {
  let component: ExerciseSearchDialogComponent;
  let fixture: ComponentFixture<ExerciseSearchDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExerciseSearchDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExerciseSearchDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
