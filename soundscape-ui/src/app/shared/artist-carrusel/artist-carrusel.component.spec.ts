import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistCarruselComponent } from './artist-carrusel.component';

describe('ArtistCarruselComponent', () => {
  let component: ArtistCarruselComponent;
  let fixture: ComponentFixture<ArtistCarruselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArtistCarruselComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArtistCarruselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
