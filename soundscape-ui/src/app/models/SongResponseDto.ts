export interface Song {
  id: number;
  songName: string;
  releaseDate: string;
  duration: string;
  isExplicit: boolean;
  lyrics: string;
  producer: string;
  streamingUrl: string;
  album: string;
  albumImage: string;
  artists: string[];
}
