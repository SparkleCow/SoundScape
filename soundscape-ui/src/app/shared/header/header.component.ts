import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  isAuthenticated: boolean = false;
  isLoading: boolean = true;
  username: string = '';
  profileImgUrl: string = '';

  constructor(private router:Router,
              private authenticationService: AuthenticationService,
              private cdr: ChangeDetectorRef
  ){}

  ngOnInit(): void {
    this.isLoading = false;
    this.authenticationService.getUserInformation$().subscribe({
      next: (user) => {
        this.username = user.username;
        this.profileImgUrl = user.profileImageUrl;
        this.isAuthenticated = true;
        this.cdr.detectChanges();
      },
      error: () => {
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  navegateToLogin(){
    this.router.navigate(["/login"]);
  }

  navegateToRegister(){
    this.router.navigate(["/register"]);
  }

  navegateToMain(){
    this.router.navigate(["/"]);
  }
}
