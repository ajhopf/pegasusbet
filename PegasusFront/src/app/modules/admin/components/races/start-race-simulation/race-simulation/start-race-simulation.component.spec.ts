import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StartRaceSimulationComponent } from './start-race-simulation.component';

describe('RaceSimulationComponent', () => {
  let component: StartRaceSimulationComponent;
  let fixture: ComponentFixture<StartRaceSimulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StartRaceSimulationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StartRaceSimulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
