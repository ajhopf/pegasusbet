import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformationOverlayComponent } from './information-overlay.component';

describe('InformationOverlayComponent', () => {
  let component: InformationOverlayComponent;
  let fixture: ComponentFixture<InformationOverlayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InformationOverlayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InformationOverlayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
