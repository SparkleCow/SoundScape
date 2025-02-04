package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.album.AlbumArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;

public class ArtistMapper {

    public static ArtistResponseDto toArtistResponseDto(Artist artist){
        return ArtistResponseDto.builder()
                .id(artist.getId())
                .description(artist.getDescription())
                .profileImageUrl(artist.getProfileImageUrl())
                .bannerImageUrl(artist.getBannerImageUrl())
                .debutYear(artist.getDebutYear())
                .isVerified(artist.getIsVerified())
                .followers(artist.getFollowersCount())
                .socialMediaUrls(artist.getSocialMediaUrls())
                .genres(artist.getGenres())
                //Map albums to albumsArtistRequestDto in order to send useful information in the ArtistResponseDto
                .albums(artist.getAlbums().stream()
                        .map(x-> AlbumArtistResponseDto.builder().id(x.getId()).albumName(x.getAlbumName()).build()).toList())
                .build();

    }

    public static Artist toArtist(ArtistRequestDto artistRequestDto){
        return Artist.builder()
                .artistName(artistRequestDto.artistName())
                .description(artistRequestDto.description())
                .profileImageUrl(artistRequestDto.profileImageUrl())
                .bannerImageUrl(artistRequestDto.bannerImageUrl())
                .debutYear(artistRequestDto.debutYear())
                .isVerified(false)
                .socialMediaUrls(artistRequestDto.socialMediaUrls())
                .genres(artistRequestDto.genres())
                .websiteUrl(artistRequestDto.websiteUrl())
                .build();
    }
}
