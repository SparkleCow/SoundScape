import { Component, Input, OnInit } from '@angular/core';
import { SongService } from '../../services/song.service';
import { Song } from '../../models/SongResponseDto';

@Component({
  selector: 'app-carrusel',
  templateUrl: './carrusel.component.html',
  styleUrl: './carrusel.component.css'
})
export class CarruselComponent implements OnInit{

  @Input() title: String = "";
  songs: Song[] = [];

  constructor(private _songService: SongService) { }

  ngOnInit(): void {
    this._songService.findRecentSongs$().subscribe({
      next: (songs) => {
        this.songs = songs.content;
      }
    });
  }

  moveLeft(container: HTMLElement) {
    container.scrollLeft -= 150;
  }

  moveRight(container: HTMLElement) {
    container.scrollLeft += 150;
  }
}
