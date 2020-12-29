import { TestBed } from '@angular/core/testing';

import { NutrientTargetService } from './nutrient-target.service';

describe('NutrientTargetService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NutrientTargetService = TestBed.get(NutrientTargetService);
    expect(service).toBeTruthy();
  });
});
