import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NoteButtonContainerComponent } from './note-button-container.component';

describe('NoteButtonContainerComponent', () => {
  let component: NoteButtonContainerComponent;
  let fixture: ComponentFixture<NoteButtonContainerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteButtonContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteButtonContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
