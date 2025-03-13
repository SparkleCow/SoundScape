package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.album.AlbumArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;

public class ArtistMapper {

    private ArtistMapper(){}

    public static ArtistResponseDto toArtistResponseDto(Artist artist){
        return ArtistResponseDto.builder()
                .id(artist.getId())
                .artistName(artist.getArtistName())
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
                        .map(x-> AlbumArtistResponseDto.builder()
                                .id(x.getId())
                                .coverImgUrl(x.getCoverImgUrl())
                                .albumName(x.getAlbumName())
                                .build()).toList())
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

    public static Artist updateArtist(Artist artist, ArtistUpdateDto artistUpdateDto) {
        if (artistUpdateDto.description() != null && !artistUpdateDto.description().isEmpty()) {
            artist.setDescription(artistUpdateDto.description());
        }
        if (artistUpdateDto.profileImageUrl() != null && !artistUpdateDto.profileImageUrl().isEmpty()) {
            artist.setProfileImageUrl(artistUpdateDto.profileImageUrl());
        }
        if (artistUpdateDto.bannerImageUrl() != null && !artistUpdateDto.bannerImageUrl().isEmpty()) {
            artist.setBannerImageUrl(artistUpdateDto.bannerImageUrl());
        }
        if (artistUpdateDto.socialMediaUrls() != null && !artistUpdateDto.socialMediaUrls().isEmpty()) {
            artist.setSocialMediaUrls(artistUpdateDto.socialMediaUrls());
        }
        if (artistUpdateDto.genres() != null && !artistUpdateDto.genres().isEmpty()) {
            artist.setGenres(artistUpdateDto.genres());
        }
        if (artistUpdateDto.websiteUrl() != null && !artistUpdateDto.websiteUrl().isEmpty()) {
            artist.setWebsiteUrl(artistUpdateDto.websiteUrl());
        }
        return artist;
    }
}
