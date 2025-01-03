import { TestBed } from '@angular/core/testing';

import { RacecourseService } from './racecourse.service';

describe('RacecourseService', () => {
  let service: RacecourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RacecourseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
