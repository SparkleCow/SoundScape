export interface Song {
  id: number;
  songName: string;
  releaseDate: string;
  duration: string;
  isExplicit: boolean;
  lyrics: string;
  producer: string;
  streamingUrl: { [key: string]: string };
  album: string;
  albumImage: string;
  artists: string[];
}
