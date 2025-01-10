import { Component } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { UserRequestDto } from '../../models/UserRequestDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent {

  userForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthenticationService, private router: Router) {
    this.userForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthDate: ['', [Validators.required]],
      country: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      const userRequestDto: UserRequestDto = this.userForm.value;
      this.authService.register$(userRequestDto).subscribe({
        next: (response) => {
          console.log('Registro exitoso:', response);
          alert('Usuario registrado con éxito');
          this.navigateToLogin();
        },
        error: (error) => {
          console.error('Error en el registro:', error);
          alert('Ocurrió un error al registrar el usuario');
          this.resetForm();
        }
      });
    } else {
      alert('Por favor, completa todos los campos correctamente.');
    }
  }

  resetForm(){
    this.userForm.reset();
  }

  navigateToLogin(){
    this.router.navigate(["/login"]);
  }
}
