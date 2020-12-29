import { TestBed } from '@angular/core/testing';

import { NutrientProgressService } from './nutrient-progress.service';

describe('NutrientProgressService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NutrientProgressService = TestBed.get(NutrientProgressService);
    expect(service).toBeTruthy();
  });
});
