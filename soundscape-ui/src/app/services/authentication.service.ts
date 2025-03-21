import { AuthenticationRequestDto } from './../models/AuthenticationRequestDto';
import { UserRequestDto } from './../models/UserRequestDto';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
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
  private userSubject = new BehaviorSubject<UserResponseDto | null>(null);
  user$ = this.userSubject.asObservable();

  constructor(private httpClient: HttpClient,
              private toastr: ToastrService){
    this.apiUrl = environment.apiUrl
  }

  login$(authenticationRequestDto: AuthenticationRequestDto): Observable<AuthenticationResponseDto> {
    return this.httpClient.post<AuthenticationResponseDto>(`${this.apiUrl}${this.authenticationPath}/login`, authenticationRequestDto).pipe(
      tap((response) => {
        localStorage.setItem("token", response.token);
        this.getUserInformation$().subscribe(user => this.userSubject.next(user));
      })
    );
  }

  logout(): void {
    localStorage.removeItem("token");
    this.userSubject.next(null);
  }

  register$(userRequestDto: UserRequestDto): Observable<UserResponseDto> {
    return this.httpClient.post<UserResponseDto>(`${this.apiUrl}${this.authenticationPath}/register`, userRequestDto);
  }

  validateToken$(token: TokenRequestDto): Observable<any>{
    return this.httpClient.post<any>(`${this.apiUrl}${this.authenticationPath}/validate-token`, token);
  }

  getUserInformation$(): Observable<UserResponseDto> {
    return this.httpClient.get<UserResponseDto>(`${this.apiUrl}/user/me`).pipe(
      tap(user => this.userSubject.next(user)),
      catchError((error: HttpErrorResponse) => throwError(() => new Error('Error al obtener la información del usuario ' + error)))
    );
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem("token");
    if (!token) return false;
    try {
      const payloadBase64 = token.split(".")[1];
      const decodedPayload = JSON.parse(atob(payloadBase64));
      const currentTime = Math.floor(Date.now() / 1000);
      return decodedPayload.exp > currentTime;
    } catch (error) {
      console.error("Error al decodificar el token", error);
      return false;
    }
  }

  get token(){
    return localStorage.getItem("token") as string;
  }

}

