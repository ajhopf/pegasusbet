import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoneyConversionComponent } from './money-conversion.component';

describe('MoneyConversionComponent', () => {
  let component: MoneyConversionComponent;
  let fixture: ComponentFixture<MoneyConversionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoneyConversionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoneyConversionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
