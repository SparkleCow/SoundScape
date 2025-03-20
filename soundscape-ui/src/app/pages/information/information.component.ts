import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrl: './information.component.css'
})
export class InformationComponent implements OnInit{

  isArtistPage: boolean = false;
  isAlbumPage: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.checkUrl();
    this.router.events.subscribe(() => {
      this.checkUrl();
      console.log(this.isArtistPage)
    });
  }

  checkUrl() {
    const currentUrl = this.router.url;
    this.isArtistPage = currentUrl.includes('/artist');
    this.isAlbumPage = currentUrl.includes('/album');
  }
}
