package com.sparklecow.soundscape.models.song;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

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