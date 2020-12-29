import { TestBed } from '@angular/core/testing';

import { EntryService } from './entry.service';

describe('EntryCountService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EntryService = TestBed.get(EntryService);
    expect(service).toBeTruthy();
  });
});
