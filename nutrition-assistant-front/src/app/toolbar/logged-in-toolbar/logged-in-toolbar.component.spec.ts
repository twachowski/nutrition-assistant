import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { LoggedInToolbarComponent } from './logged-in-toolbar.component';

describe('LoggedInToolbarComponent', () => {
  let component: LoggedInToolbarComponent;
  let fixture: ComponentFixture<LoggedInToolbarComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ LoggedInToolbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoggedInToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
