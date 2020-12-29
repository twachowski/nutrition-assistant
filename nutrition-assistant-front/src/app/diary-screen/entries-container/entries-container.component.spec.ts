import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EntriesContainerComponent } from './entries-container.component';

describe('EntriesContainerComponent', () => {
  let component: EntriesContainerComponent;
  let fixture: ComponentFixture<EntriesContainerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EntriesContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EntriesContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
