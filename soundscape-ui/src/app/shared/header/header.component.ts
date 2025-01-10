import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  title:String = "SoundScape";
  isAuthenticated: boolean = false;

  constructor(private router:Router){}

  navegateToLogin(){
    this.router.navigate(["/login"]);
  }

  navegateToRegister(){
    this.router.navigate(["/register"]);
  }
}
