import { AuthenticationRequestDto } from './../models/AuthenticationRequestDto';
import { UserRequestDto } from './../models/UserRequestDto';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { AuthenticationResponseDto } from '../models/AuthenticationResponseDto';
import { UserResponseDto } from '../models/UserResponseDto';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly apiUrl: string;
  private readonly authenticationPath = '/auth';

  constructor(private httpClient: HttpClient) {
    this.apiUrl = environment.apiUrl
  }

  login$(authenticationRequestDto: AuthenticationRequestDto): Observable<AuthenticationResponseDto> {
    return this.httpClient.post<AuthenticationResponseDto>(`${this.apiUrl}${this.authenticationPath}/login`, authenticationRequestDto);
  }

  register$(userRequestDto: UserRequestDto): Observable<UserResponseDto> {
    return this.httpClient.post<UserResponseDto>(`${this.apiUrl}${this.authenticationPath}/register`, userRequestDto);
  }

  set token(token:string){
    localStorage.setItem("token", token)
  }

  get token(){
    return localStorage.getItem("token") as string;
  }

  logout(): void {
    localStorage.removeItem("token");
  }
}
