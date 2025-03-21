import { AlbumResponseDto } from '../../models/AlbumResponseDto';
import { Song } from '../../models/SongResponseDto';
import { Album } from './../../models/ArtistResponseDto';
import { AlbumService } from './../../services/album.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrl: './album.component.css'
})
export class AlbumComponent implements OnInit{

  selectedTrackUrl: string | null = null;
  albumId!: number;
  album!: AlbumResponseDto;
  songs: Song[] = [];

  constructor(private router:Router, private activateRouter:ActivatedRoute, private albumService:AlbumService){}

  ngOnInit(): void {
    this.albumId =  Number(this.activateRouter.snapshot.paramMap.get('id'));

    this.albumService.findAlbumById$(this.albumId).subscribe({
      next: (album) => {
        this.album = album;
        this.songs = album.songs;
      }
    });
  }
}
