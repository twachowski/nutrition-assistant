import { TestBed } from '@angular/core/testing';

import { NutrientDetailsService } from './nutrient-details.service';

describe('NutrientDetailsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NutrientDetailsService = TestBed.get(NutrientDetailsService);
    expect(service).toBeTruthy();
  });
});
