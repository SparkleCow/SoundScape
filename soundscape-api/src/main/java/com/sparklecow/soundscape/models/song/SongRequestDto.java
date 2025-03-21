package com.sparklecow.soundscape.models.song;

import java.time.LocalDate;
import java.time.LocalTime;

public record SongRequestDto(
        String songName,
        LocalDate releaseDate,
        LocalTime duration,
        Boolean isExplicit,
        String lyrics,
        String producer,
        String streamingUrl,
        String album
) {
}