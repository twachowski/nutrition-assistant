import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DialogWithToolbarComponent } from './dialog-with-toolbar.component';

describe('DialogWithToolbarComponent', () => {
  let component: DialogWithToolbarComponent;
  let fixture: ComponentFixture<DialogWithToolbarComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogWithToolbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogWithToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
