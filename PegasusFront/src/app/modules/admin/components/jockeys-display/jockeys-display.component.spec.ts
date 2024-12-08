import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JockeysDisplayComponent } from './jockeys-display.component';

describe('JockeysDisplayComponent', () => {
  let component: JockeysDisplayComponent;
  let fixture: ComponentFixture<JockeysDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JockeysDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JockeysDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
