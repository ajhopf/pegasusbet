import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacesPageComponent } from './races-page.component';

describe('RacesPageComponent', () => {
  let component: RacesPageComponent;
  let fixture: ComponentFixture<RacesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RacesPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
