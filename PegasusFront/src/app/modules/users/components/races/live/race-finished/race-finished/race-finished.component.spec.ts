import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceFinishedComponent } from './race-finished.component';

describe('RaceFinishedComponent', () => {
  let component: RaceFinishedComponent;
  let fixture: ComponentFixture<RaceFinishedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceFinishedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RaceFinishedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
