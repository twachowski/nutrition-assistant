import { TestBed } from '@angular/core/testing';

import { DialogConfigService } from './dialog-config.service';

describe('DialogConfigService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DialogConfigService = TestBed.get(DialogConfigService);
    expect(service).toBeTruthy();
  });
});
