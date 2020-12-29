import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { VitaminDetailsComponent } from './vitamin-details.component';

describe('VitaminDetailsComponent', () => {
  let component: VitaminDetailsComponent;
  let fixture: ComponentFixture<VitaminDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ VitaminDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VitaminDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
