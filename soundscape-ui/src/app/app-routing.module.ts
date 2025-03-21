import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ActivationComponent } from './pages/activation/activation.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { ArtistInformationComponent } from './shared/artist-information/artist-information.component';
import { InformationComponent } from './pages/information/information.component';

const routes: Routes = [
  {path: "", component: MainComponent},
  {path: "register", component: RegisterComponent},
  {path: "login", component: LoginComponent},
  {path: "activate-token", component: ActivationComponent},
  {path: "perfil", component: PerfilComponent},
  {path: "artist/:id", component: InformationComponent},
  {path: "album/:id", component: InformationComponent},
  {path: "playlist/:id", component: InformationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
