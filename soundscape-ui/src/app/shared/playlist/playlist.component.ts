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

  generalPlaylist: PlaylistNameByOwnerDto[] = [];
  userPlaylists: PlaylistNameByOwnerDto[] = [];
  playlists: PlaylistNameByOwnerDto[] = [];
  isAuthenticated: boolean = false;

  constructor(private authenticationService:AuthenticationService,
              private playlistService: PlaylistService
  ){}


  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      this.playlistService.findMyPlaylist$().subscribe({
        next: (playlist) => this.userPlaylists= playlist.content,
        error: err => console.error(err)
      });
    }
    this.playlistService.findGeneralPlaylist$().subscribe({
      next: (playlist) => {
        this.generalPlaylist = playlist.content,
        this.playlists = this.userPlaylists.concat(this.generalPlaylist)
      },
      error: err => console.error(err)
    });


  }
}
