import { Component, Input, OnInit } from '@angular/core';
import { ArtistResponseDto } from '../../models/ArtistResponseDto';

@Component({
  selector: 'app-aside-information',
  templateUrl: './aside-information.component.html',
  styleUrl: './aside-information.component.css'
})
export class AsideInformationComponent{

  @Input() artists: ArtistResponseDto[] = [];
}
