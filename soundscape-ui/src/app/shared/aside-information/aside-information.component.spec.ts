import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsideInformationComponent } from './aside-information.component';

describe('AsideInformationComponent', () => {
  let component: AsideInformationComponent;
  let fixture: ComponentFixture<AsideInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AsideInformationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AsideInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
