import { Component, OnInit } from '@angular/core';
import { AlbumResponseDto } from '../../models/AlbumResponseDto';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit{

  albums: AlbumResponseDto[] = [];

  constructor(private ){}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
