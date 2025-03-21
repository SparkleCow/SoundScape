import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../environments/environment';
import { PlaylistNameByOwnerDto } from '../models/Playlist';
import { Page } from '../models/Page';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  private readonly apiUrl: string;

   constructor(private httpClient: HttpClient,
                private toastr: ToastrService){
      this.apiUrl = environment.apiUrl
  }

  findMyPlaylist$(): Observable<Page<PlaylistNameByOwnerDto>> {
      return this.httpClient.get<Page<PlaylistNameByOwnerDto>>(`${this.apiUrl}/playlist/me`);
  }

  findGeneralPlaylist$(): Observable<Page<PlaylistNameByOwnerDto>> {
    return this.httpClient.get<Page<PlaylistNameByOwnerDto>>(`${this.apiUrl}/playlist?username=Admin`);
}
}


