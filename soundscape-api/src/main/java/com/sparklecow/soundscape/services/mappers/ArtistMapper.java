package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;

public class ArtistMapper {

    public static ArtistResponseDto toArtistResponseDto(Artist artist){
        return new ArtistResponseDto(
                artist.getId(),
                artist.getArtistName(),
                artist.getDescription(),
                artist.getProfileImageUrl(),
                artist.getBannerImageUrl(),
                artist.getDebutYear(),
                artist.getIsVerified(),
                artist.getFollowersCount(),
                artist.getSocialMediaUrls(),
                artist.getGenres(),
                artist.getAlbums(),
                artist.getWebsiteUrl()
        );
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
