import { Component, Input } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrl: './player.component.css'
})
export class PlayerComponent {
  private _streamingUrl: string | null = null;
  sanitizedUrl: SafeResourceUrl | null = null;

  constructor(private sanitizer: DomSanitizer) {}

  @Input() set streamingUrl(url: string | null) {
    this._streamingUrl = url;
    this.sanitizedUrl = url ? this.sanitizer.bypassSecurityTrustResourceUrl(url) : null;
  }}
