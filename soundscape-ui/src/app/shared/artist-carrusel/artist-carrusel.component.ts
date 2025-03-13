import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ArtistResponseDto } from '../../models/ArtistResponseDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-artist-carrusel',
  templateUrl: './artist-carrusel.component.html',
  styleUrl: './artist-carrusel.component.css'
})
export class ArtistCarruselComponent implements OnInit, OnChanges{

  @ViewChild('carrusel', { static: true }) carrusel!: ElementRef;
  @Input() artists: ArtistResponseDto[] = [];
  duplicatedArtists: ArtistResponseDto[] = [];
  scrollDirection: 'left' | 'right' = 'right';

  constructor(private router:Router){}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['artists'] && this.artists.length > 0) {
      this.duplicateArtists();
    }
  }

  ngOnInit() {
    console.log("Desde carrusel" + this.artists);
  }

  duplicateArtists() {
    this.duplicatedArtists = [...this.artists, ...this.artists];
  }

  navigateAtArtist(artistId: number){
    this.router.navigate([`/artist/${artistId}`])
  }

  startAutoScroll() {
    const carrusel = this.carrusel.nativeElement;
    const scrollAmount = 20;
    const scrollDelay = 100;

    const scroll = () => {
      if (this.scrollDirection === 'right') {
        carrusel.scrollLeft += scrollAmount;

        if (carrusel.scrollLeft + carrusel.clientWidth >= carrusel.scrollWidth) {
          setTimeout(() => {
            carrusel.scrollLeft = 0;
          }, scrollDelay);
        }
      } else {
        carrusel.scrollLeft -= scrollAmount;

        if (carrusel.scrollLeft <= 0) {
          this.scrollDirection = 'right';
        }
      }

      setTimeout(() => {
        requestAnimationFrame(scroll);
      }, scrollDelay);
    };

    requestAnimationFrame(scroll);
  }
}
