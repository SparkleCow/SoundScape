import { ArtistService } from './../../services/artist.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArtistResponseDto } from '../../models/ArtistResponseDto';

@Component({
  selector: 'app-artist-information',
  templateUrl: './artist-information.component.html',
  styleUrl: './artist-information.component.css'
})
export class ArtistInformationComponent implements OnInit{

  artist!: ArtistResponseDto;
  artistId!: number;

  constructor(private router:Router, private activateRouter:ActivatedRoute, private artistService:ArtistService){}

  ngOnInit(): void {
    this.artistId =  Number(this.activateRouter.snapshot.paramMap.get('id'));

    this.artistService.findArtist$(this.artistId).subscribe({
      next: (artist) => {
        this.artist = artist;
      }
    });
  }
}
