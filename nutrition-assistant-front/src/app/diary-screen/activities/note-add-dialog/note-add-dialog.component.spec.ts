import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NoteAddDialogComponent } from './note-add-dialog.component';

describe('NoteAddDialogComponent', () => {
  let component: NoteAddDialogComponent;
  let fixture: ComponentFixture<NoteAddDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteAddDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteAddDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
