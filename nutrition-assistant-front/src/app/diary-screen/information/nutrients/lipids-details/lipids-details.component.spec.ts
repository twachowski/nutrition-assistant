import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { LipidsDetailsComponent } from './lipids-details.component';

describe('LipidsDetailsComponent', () => {
  let component: LipidsDetailsComponent;
  let fixture: ComponentFixture<LipidsDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ LipidsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LipidsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
