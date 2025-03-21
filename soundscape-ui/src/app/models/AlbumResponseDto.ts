import { Song } from "./SongResponseDto";

export interface AlbumResponseDto {
  id: number;
  albumName: string;
  coverImgUrl: string;
  releaseDate: string;
  isExplicit: boolean;
  artists: string[];
  songs: Song[];
}

