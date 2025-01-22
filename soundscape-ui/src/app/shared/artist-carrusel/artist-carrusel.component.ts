import { Component, Input } from '@angular/core';
import { ArtistResponseDto } from '../../models/ArtistResponseDto';

@Component({
  selector: 'app-artist-carrusel',
  templateUrl: './artist-carrusel.component.html',
  styleUrl: './artist-carrusel.component.css'
})
export class ArtistCarruselComponent {

  @Input() artists: ArtistResponseDto[] = [];

}
