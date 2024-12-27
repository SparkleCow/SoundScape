package com.sparklecow.soundscape.models.artist;

import com.sparklecow.soundscape.models.common.Genre;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

//TODO Validations
public record ArtistRequestDto(

        String artistName,

        String description,

        String profileImageUrl,

        String bannerImageUrl,

        LocalDate debutYear,

        Map<String, String> socialMediaUrls,

        Set<Genre> genres,

        String websiteUrl
){
}
