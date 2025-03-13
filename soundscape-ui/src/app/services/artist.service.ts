import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { ArtistResponseDto } from '../models/ArtistResponseDto';
import { Page } from '../models/Page';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  apiUrl: string;

  constructor(private httpClient: HttpClient) {
    this.apiUrl = environment.apiUrl;
  }

  findArtists$(page?: number, artistName?: string): Observable<Page<ArtistResponseDto>> {
    let url = `${this.apiUrl}/artist`;
    if (page !== undefined) {
      url += `?page=${page}`;
    }
    if (artistName) {
      url += page !== undefined ? `&name=${artistName}` : `?name=${artistName}`;
    }
    return this.httpClient.get<Page<ArtistResponseDto>>(url);
  }

  findArtist$(artistId: number): Observable<ArtistResponseDto>{
    return this.httpClient.get<ArtistResponseDto>(`${this.apiUrl}/artist/${artistId}`);
  }
}
