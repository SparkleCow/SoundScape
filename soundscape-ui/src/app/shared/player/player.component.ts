import { Component, Input, OnInit, ElementRef, ViewChild } from '@angular/core';

// Extender el tipo global Window para incluir onYouTubeIframeAPIReady
declare global {
  interface Window {
    onYouTubeIframeAPIReady?: () => void;
  }
}

declare const YT: any; // Declaración para el objeto de YouTube IFrame API

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  @Input() videoUrl: null|string = null; // Recibe el link de YouTube
  @ViewChild('youtubePlayer', { static: true }) youtubePlayer!: ElementRef;

  player: any;
  videoId: string = '';

  ngOnInit(): void {
    if (this.videoUrl) {
      this.videoId = this.extractVideoId(this.videoUrl);
      this.loadYouTubeAPI();
    }
  }

  ngOnChanges(): void {
    if (this.videoUrl && this.player) {
      this.videoId = this.extractVideoId(this.videoUrl);
      this.player.cueVideoById(this.videoId); // Cambia el video actual
    }
  }

  private loadYouTubeAPI(): void {
    const tag = document.createElement('script');
    tag.src = 'https://www.youtube.com/iframe_api';
    document.body.appendChild(tag);

    window.onYouTubeIframeAPIReady = () => this.createPlayer();
  }

  private createPlayer(): void {
    this.player = new YT.Player(this.youtubePlayer.nativeElement, {
      height: '50',
      width: '100%',
      videoId: this.videoId,
      playerVars: {
        autoplay: 1,
        controls: 1,
        modestbranding: 1,
        rel: 0,
        showinfo: 0,
        iv_load_policy: 3
      },
      events: {
        onReady: (event: any) => {
          event.target.playVideo(); // Reproduce automáticamente
        }
      }
    });
  }

  private extractVideoId(url: string): string {
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match = url.match(regExp);
    return match && match[2].length === 11 ? match[2] : '';
  }
}
