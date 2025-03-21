import { ToastrModule, ToastrService } from 'ngx-toastr';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { AuthenticationRequestDto } from '../../models/AuthenticationRequestDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthenticationService, private router:Router, private toastr:ToastrService) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      const loginRequest: AuthenticationRequestDto = this.loginForm.value;
      this.authService.login$(loginRequest).subscribe({
        next: (response) => {
          this.toastr.success("Bienvenido")
          this.navigateToMain();
        },
        error: (error) => {
          console.error('Error en el inicio de sesión:', error);
          this.toastr.success('Usuario o contraseña incorrecta')
        }
      });
    } else {
      this.toastr.success('Por favor, completa todos los campos correctamente.')
    }
  }

  navigateToMain(){
    this.router.navigate([""]);
  }
}
