import { TestBed } from '@angular/core/testing';

import { RaceSimulationService } from './race-simulation.service';

describe('RaceSimulationService', () => {
  let service: RaceSimulationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RaceSimulationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
