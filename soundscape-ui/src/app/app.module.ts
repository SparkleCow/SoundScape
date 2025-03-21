import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { RegisterComponent } from './pages/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ActivationComponent } from './pages/activation/activation.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { ArtistCarruselComponent } from './shared/artist-carrusel/artist-carrusel.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { AsideInformationComponent } from './shared/aside-information/aside-information.component';
import { CarruselComponent } from './shared/carrusel/carrusel.component';
import { FooterComponent } from './shared/footer/footer.component';
import { ArtistInformationComponent } from './shared/artist-information/artist-information.component';
import { InformationComponent } from './pages/information/information.component';
import { PlaylistComponent } from './shared/playlist/playlist.component';
import { PlaylistSongsComponent } from './shared/playlist-songs/playlist-songs.component';
import { AlbumComponent } from './shared/album/album.component';
import { PlayerComponent } from './shared/player/player.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    MainComponent,
    RegisterComponent,
    ActivationComponent,
    ArtistCarruselComponent,
    PerfilComponent,
    AsideInformationComponent,
    CarruselComponent,
    FooterComponent,
    ArtistInformationComponent,
    InformationComponent,
    PlaylistComponent,
    PlaylistSongsComponent,
    AlbumComponent,
    PlayerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
