import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ElementaryProgressComponent } from './elementary-progress.component';

describe('ElementaryProgressComponent', () => {
  let component: ElementaryProgressComponent;
  let fixture: ComponentFixture<ElementaryProgressComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ElementaryProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ElementaryProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
