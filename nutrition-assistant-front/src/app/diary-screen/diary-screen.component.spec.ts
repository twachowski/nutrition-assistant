import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DiaryScreenComponent } from './diary-screen.component';

describe('DiaryScreenComponent', () => {
  let component: DiaryScreenComponent;
  let fixture: ComponentFixture<DiaryScreenComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DiaryScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiaryScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
