export interface AlbumResponseDto {
  id: number;
  albumName: string;
  coverImgUrl: string;
  releaseDate: string;
  isExplicit: boolean;
  artists: string[];
  songs: Song[];
}

export interface Song {
  id: number;
  title: string;
  duration: number;
  explicit: boolean;
}
