package com.sparklecow.soundscape.models.album;

import lombok.Builder;

@Builder
public record AlbumArtistResponseDto(
        Long id,
        String albumName
) {
}
