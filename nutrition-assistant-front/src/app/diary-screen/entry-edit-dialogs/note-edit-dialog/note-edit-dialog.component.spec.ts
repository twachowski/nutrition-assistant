import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NoteEditDialogComponent } from './note-edit-dialog.component';

describe('NoteEditDialogComponent', () => {
  let component: NoteEditDialogComponent;
  let fixture: ComponentFixture<NoteEditDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteEditDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
