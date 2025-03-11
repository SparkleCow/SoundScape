import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Song } from '../models/SongResponseDto';
import { Observable } from 'rxjs';
import { Page } from '../models/Page';

@Injectable({
  providedIn: 'root'
})
export class SongService {

  apiUrl: string;

  constructor(private httpClient: HttpClient) {
    this.apiUrl = environment.apiUrl
  }

  findRecentSongs$(): Observable<Page<Song>>{
    return this.httpClient.get<Page<Song>>(`${this.apiUrl}/song`);
  }
}
