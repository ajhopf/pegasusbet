import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceBetSidebarComponent } from './place-bet-sidebar.component';

describe('SidebarContentComponent', () => {
  let component: PlaceBetSidebarComponent;
  let fixture: ComponentFixture<PlaceBetSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaceBetSidebarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlaceBetSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
