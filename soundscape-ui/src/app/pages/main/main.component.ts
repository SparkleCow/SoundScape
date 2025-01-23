import { ArtistResponseDto } from '../../models/ArtistResponseDto';
import { ArtistService } from './../../services/artist.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit{

  artists: ArtistResponseDto[] = [];

  constructor(private _artistService: ArtistService){}

  ngOnInit(): void {
    this.loadArtist();
  }

  loadArtist(){
    this._artistService.findArtists$().subscribe({
      next: (page) => {
        this.artists = page.content;
      }
    });
  }
}
