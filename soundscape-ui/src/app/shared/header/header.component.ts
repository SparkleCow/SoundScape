import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { Role } from '../../models/Role';

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
  role: Role[] = [];
  isAdmin: boolean = false;

  constructor(private router:Router,
              private authenticationService: AuthenticationService,
              private cdr: ChangeDetectorRef
  ){}

  ngOnInit(): void {
    this.isLoading = false;
    this.authenticationService.user$.subscribe(user => {
      if (user) {
        this.username = user.username;
        this.profileImgUrl = user.profileImageUrl;
        this.isAuthenticated = true;
        this.role = user.role;
        this.checkRoles();
      } else {
        this.isAuthenticated = false;
      }
      this.cdr.detectChanges();
    });
  }

  logout(){
    this.authenticationService.logout();
    window.location.reload();
  }

  checkRoles(){
    this.isAdmin = this.role.some(rol => rol.roleName === "ADMIN");
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
