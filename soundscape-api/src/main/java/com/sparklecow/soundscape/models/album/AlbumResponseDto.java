package com.sparklecow.soundscape.models.album;

import com.sparklecow.soundscape.entities.song.Song;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AlbumResponseDto(
        Long id,

        String albumName,

        String coverImgUrl,

        LocalDate releaseDate,

        Boolean isExplicit,

        List<String> artists,

        List<Song> songs
) {
}
