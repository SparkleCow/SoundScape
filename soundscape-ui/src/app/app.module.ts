import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { RegisterComponent } from './pages/register/register.component';
import { ReactiveFormsModule } from '@angular/forms'; // Importa ReactiveFormsModule
import { provideHttpClient } from '@angular/common/http';
import { ActivationComponent } from './pages/activation/activation.component';  // Importar provideHttpClient


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    MainComponent,
    RegisterComponent,
    ActivationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
