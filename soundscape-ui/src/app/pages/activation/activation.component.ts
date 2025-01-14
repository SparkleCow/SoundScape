import { Router } from '@angular/router';
import { AuthenticationService } from './../../services/authentication.service';
import { Component, ElementRef, QueryList, ViewChildren } from '@angular/core';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrl: './activation.component.css'
})
export class ActivationComponent {

  @ViewChildren('digit0, digit1, digit2, digit3, digit4, digit5') inputs!: QueryList<ElementRef>;

  constructor(private authenticationService: AuthenticationService,
              private router: Router){}

  onInputChange(event: Event, index: number): void {
    const input = event.target as HTMLInputElement;
    if (input.value.length === 1 && index < 5) {
      this.inputs.toArray()[index + 1].nativeElement.focus();
    } else if (input.value.length === 0 && index > 0) {
      this.inputs.toArray()[index - 1].nativeElement.focus();
    }
  }

  onSubmit(): void {
    const code = {token: this.inputs.toArray().map(input => input.nativeElement.value).join('')}

    this.authenticationService.validateToken$(code).subscribe({
      next: () => {
        alert('El código ha sido activado éxitosamente');
        this.navigateToMain();
      }
      ,error: (error) => {
        console.error('Error en el inicio de sesión:', error);
        alert('Usuario o contraseña incorrectos');
      }
    });
  }

  navigateToMain(){
    this.router.navigate([""]);
  }

}
