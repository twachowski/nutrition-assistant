import { TestBed } from '@angular/core/testing';

import { UserAccountActivatorService } from './user-account-activator.service';

describe('UserAccountActivatorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserAccountActivatorService = TestBed.get(UserAccountActivatorService);
    expect(service).toBeTruthy();
  });
});
