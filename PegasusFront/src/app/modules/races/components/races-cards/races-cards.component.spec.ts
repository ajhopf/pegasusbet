import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacesCardsComponent } from './races-cards.component';

describe('RacesTableComponent', () => {
  let component: RacesCardsComponent;
  let fixture: ComponentFixture<RacesCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RacesCardsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacesCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
