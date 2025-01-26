import { Component, OnInit } from '@angular/core';
import { UserResponseDto } from '../../models/UserResponseDto';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit{

  user!:UserResponseDto;
  constructor(private _authenticationService:AuthenticationService){}

  ngOnInit(): void {
    this._authenticationService.getUserInformation$().subscribe({
      next: (user) => {
        this.user = user;
      }
    });
  }
}
