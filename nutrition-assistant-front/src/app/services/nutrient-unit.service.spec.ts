import { TestBed } from '@angular/core/testing';

import { NutrientUnitService } from './nutrient-unit.service';

describe('NutrientUnitService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NutrientUnitService = TestBed.get(NutrientUnitService);
    expect(service).toBeTruthy();
  });
});
