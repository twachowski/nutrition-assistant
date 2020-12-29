import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProfileScreenComponent } from './profile-screen.component';

describe('ProfileScreenComponent', () => {
  let component: ProfileScreenComponent;
  let fixture: ComponentFixture<ProfileScreenComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
