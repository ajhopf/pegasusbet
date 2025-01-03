import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HorsesTableComponent } from './horses-table.component';

describe('HorsesDisplayComponent', () => {
  let component: HorsesTableComponent;
  let fixture: ComponentFixture<HorsesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HorsesTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HorsesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
