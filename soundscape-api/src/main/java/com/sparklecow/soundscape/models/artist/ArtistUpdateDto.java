package com.sparklecow.soundscape.models.artist;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.models.common.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO Validations
public record ArtistUpdateDto(

        String description,

        String profileImageUrl,

        String bannerImageUrl,
        Map<String, String> socialMediaUrls,

        Set<Genre> genres,

        List<Album> albums,

        String websiteUrl
) {
}
