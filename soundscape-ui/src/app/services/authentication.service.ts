import { AuthenticationRequestDto } from './../models/AuthenticationRequestDto';
import { UserRequestDto } from './../models/UserRequestDto';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AuthenticationResponseDto } from '../models/AuthenticationResponseDto';
import { UserResponseDto } from '../models/UserResponseDto';
import { TokenRequestDto } from '../models/TokenRequestDto';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly apiUrl: string;
  private readonly authenticationPath = '/auth';

  constructor(private httpClient: HttpClient,
              private toastr: ToastrService){
    this.apiUrl = environment.apiUrl
  }

  login$(authenticationRequestDto: AuthenticationRequestDto): Observable<AuthenticationResponseDto> {
    return this.httpClient.post<AuthenticationResponseDto>(`${this.apiUrl}${this.authenticationPath}/login`, authenticationRequestDto).pipe(
      tap( (response) => {localStorage.setItem("token", response.token)})
    );
  }

  logout(): void {
    localStorage.removeItem("token");
  }

  register$(userRequestDto: UserRequestDto): Observable<UserResponseDto> {
    return this.httpClient.post<UserResponseDto>(`${this.apiUrl}${this.authenticationPath}/register`, userRequestDto);
  }

  validateToken$(token: TokenRequestDto): Observable<any>{
    return this.httpClient.post<any>(`${this.apiUrl}${this.authenticationPath}/validate-token`, token);
  }

  getUserInformation$(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>(`${this.apiUrl}/user`)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            this.toastr.error('No hay usuario autenticado');
          }
          return throwError(() => new Error('Error al obtener la información del usuario'));
        })
      );
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem("token");
  }

  get token(){
    return localStorage.getItem("token") as string;
  }

}

