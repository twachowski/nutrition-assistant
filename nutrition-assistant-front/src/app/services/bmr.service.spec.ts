import { TestBed } from '@angular/core/testing';

import { BMRService } from './bmr.service';

describe('BMRService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BMRService = TestBed.get(BMRService);
    expect(service).toBeTruthy();
  });
});
