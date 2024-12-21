import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorsesDisplayComponent } from './horses-display.component';

describe('HorsesDisplayComponent', () => {
  let component: HorsesDisplayComponent;
  let fixture: ComponentFixture<HorsesDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorsesDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HorsesDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
