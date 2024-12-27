package com.sparklecow.soundscape.models.artist;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.models.common.Genre;

import java.time.LocalDate;
import java.util.*;

//TODO Validations
public record ArtistResponseDto(

    String artistName,

    String description,

    String profileImageUrl,

    String bannerImageUrl,

    LocalDate debutYear,

    Boolean isVerified,

    Long followers,

    Map<String, String> socialMediaUrls,

    Set<Genre> genres,

    List<Album> albums,

    String websiteUrl
) {
}
