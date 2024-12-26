import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorseJockeyInfoComponent } from './horse-jockey-info.component';

describe('HorseJockeyInfoTsComponent', () => {
  let component: HorseJockeyInfoComponent;
  let fixture: ComponentFixture<HorseJockeyInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorseJockeyInfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HorseJockeyInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
