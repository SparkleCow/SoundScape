export interface ArtistResponseDto {
  id: number;
  artistName: string;
  description: string;
  profileImageUrl: string;
  bannerImageUrl: string;
  debutYear: string;
  isVerified: boolean;
  followers: number;
  socialMediaUrls: { [key: string]: string };
  genres: Set<string>;
  albums: Album[];
  websiteUrl: string;
}

export interface Album {
  id: number;
  albumName: string;
  coverImgUrl: string;
  releaseDate: string;
  isExplicit: boolean;
}
