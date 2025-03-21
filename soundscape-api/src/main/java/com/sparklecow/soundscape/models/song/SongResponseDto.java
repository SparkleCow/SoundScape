package com.sparklecow.soundscape.models.song;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Builder
public record SongResponseDto(
        Long id,

        String songName,

        LocalDate releaseDate,

        LocalTime duration,

        Boolean isExplicit,

        String lyrics,

        String producer,

        String streamingUrl,

        String album,

        String albumImage,

        List<String> artists
){}
