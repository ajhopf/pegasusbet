import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderMenuBarComponent } from './header-menu-bar.component';

describe('HeaderMenuBarComponent', () => {
  let component: HeaderMenuBarComponent;
  let fixture: ComponentFixture<HeaderMenuBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderMenuBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderMenuBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
