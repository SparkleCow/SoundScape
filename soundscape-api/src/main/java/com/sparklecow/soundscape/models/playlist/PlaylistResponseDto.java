package com.sparklecow.soundscape.models.playlist;

import com.sparklecow.soundscape.entities.song.Song;
import lombok.Builder;

import java.util.List;

@Builder
public record PlaylistResponseDto(
        Long id,
        String playlistName,
        String playlistImageUrl,
        String username,
        List<Song> songs
){
}