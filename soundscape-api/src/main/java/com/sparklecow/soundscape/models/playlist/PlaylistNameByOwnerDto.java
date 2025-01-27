package com.sparklecow.soundscape.models.playlist;

import lombok.Builder;

@Builder
public record PlaylistNameByOwnerDto(
        Long id,
        String playlistName,
        String playlistImageUrl,
        String username,
        Integer songs
) {
}
