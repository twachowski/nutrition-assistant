import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MineralDetailsComponent } from './mineral-details.component';

describe('MineralDetailsComponent', () => {
  let component: MineralDetailsComponent;
  let fixture: ComponentFixture<MineralDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MineralDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MineralDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
