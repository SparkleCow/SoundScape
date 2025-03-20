import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { PlaylistNameByOwnerDto } from '../../models/Playlist';
import { PlaylistService } from '../../services/playlist.service';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrl: './playlist.component.css'
})
export class PlaylistComponent implements OnInit{

  playlists: PlaylistNameByOwnerDto[] = [];

  constructor(private authenticationService:AuthenticationService,
              private playlistService: PlaylistService
  ){}


  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      this.playlistService.findMyPlaylist$().subscribe({
        next: (playlist) => this.playlists = playlist.content,
        error: err => console.error(err)
      });
    }
  }
}
