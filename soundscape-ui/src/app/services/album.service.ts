import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AlbumResponseDto } from '../models/AlbumResponseDto';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {

  apiUrl: string;

  constructor(private httpClient: HttpClient) {
        this.apiUrl = environment.apiUrl
  }

  findRecentAlbums$():Observable<AlbumResponseDto[]>{
    return this.httpClient.get<AlbumResponseDto[]>(`${this.apiUrl}`)
  }
}
