import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  title:String = 'SoundScape';
  isAuthenticated: boolean = false;
  isLoading: boolean = true;
  username: string = '';
  profileImgUrl: string = '';

  constructor(private router:Router,
              private authenticationService: AuthenticationService
  ){}

  ngOnInit(): void {
    this.isLoading = false;
    this.authenticationService.getUserInformation$().subscribe({
      next: (user) => {
        this.username = user.username;
        this.profileImgUrl = user.profileImageUrl;
        this.isAuthenticated = true;
      },
    });
  }

  navegateToLogin(){
    this.router.navigate(["/login"]);
  }

  navegateToRegister(){
    this.router.navigate(["/register"]);
  }
}
