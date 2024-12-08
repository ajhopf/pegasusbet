import { TestBed } from '@angular/core/testing';

import { JockeyService } from './jockey.service';

describe('JockeyService', () => {
  let service: JockeyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JockeyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
