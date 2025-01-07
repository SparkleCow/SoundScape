package com.sparklecow.soundscape.models.album;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AlbumRequestDto(

        String albumName,

        String coverImgUrl,

        LocalDate releaseDate,

        Boolean isExplicit,

        List<String> artists
) {
}
