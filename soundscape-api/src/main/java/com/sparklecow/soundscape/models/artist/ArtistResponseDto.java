package com.sparklecow.soundscape.models.artist;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.models.album.AlbumArtistResponseDto;
import com.sparklecow.soundscape.models.common.Genre;
import lombok.Builder;

import java.time.LocalDate;
import java.util.*;

@Builder
public record ArtistResponseDto(
        Long id,

        String artistName,

        String description,

        String profileImageUrl,

        String bannerImageUrl,

        LocalDate debutYear,

        Boolean isVerified,

        Long followers,

        Map<String, String> socialMediaUrls,

        Set<Genre> genres,

        List<AlbumArtistResponseDto> albums,

        String websiteUrl
) {
}
