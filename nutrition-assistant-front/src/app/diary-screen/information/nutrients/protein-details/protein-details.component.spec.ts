import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProteinDetailsComponent } from './protein-details.component';

describe('ProteinDetailsComponent', () => {
  let component: ProteinDetailsComponent;
  let fixture: ComponentFixture<ProteinDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProteinDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProteinDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
