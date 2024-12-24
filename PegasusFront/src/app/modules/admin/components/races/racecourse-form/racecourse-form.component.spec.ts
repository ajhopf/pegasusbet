import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacecourseFormComponent } from './racecourse-form.component';

describe('RacecourseFormComponent', () => {
  let component: RacecourseFormComponent;
  let fixture: ComponentFixture<RacecourseFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RacecourseFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RacecourseFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
