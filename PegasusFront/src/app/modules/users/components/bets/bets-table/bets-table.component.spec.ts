import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetsTableComponent } from './bets-table.component';

describe('BetsTableComponent', () => {
  let component: BetsTableComponent;
  let fixture: ComponentFixture<BetsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BetsTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BetsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
