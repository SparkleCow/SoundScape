export interface Song {
  id: number;
  songName: string;
  releaseDate: string; // O usa Date si lo vas a convertir
  duration: string;
  isExplicit: boolean;
  lyrics: string;
  producer: string;
  streamingUrl: { [key: string]: string }; // Claves dinámicas para múltiples plataformas
  album: string;
  albumImage: string;
  artists: string[];
}
