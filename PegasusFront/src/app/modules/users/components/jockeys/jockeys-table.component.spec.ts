import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JockeysTableComponent } from './jockeys-table.component';

describe('JockeysDisplayComponent', () => {
  let component: JockeysTableComponent;
  let fixture: ComponentFixture<JockeysTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JockeysTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JockeysTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
