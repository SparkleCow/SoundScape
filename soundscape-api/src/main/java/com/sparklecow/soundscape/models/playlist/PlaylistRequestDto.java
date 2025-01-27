package com.sparklecow.soundscape.models.playlist;

public record PlaylistRequestDto(
    String playlistName,
    String playlistImageUrl,
    boolean isPublic
){
}