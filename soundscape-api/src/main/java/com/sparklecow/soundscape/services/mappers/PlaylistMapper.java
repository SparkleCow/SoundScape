package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.playlist.Playlist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.playlist.PlaylistNameByOwnerDto;
import com.sparklecow.soundscape.models.playlist.PlaylistRequestDto;
import com.sparklecow.soundscape.models.playlist.PlaylistResponseDto;

import java.util.List;

public class PlaylistMapper {

    private PlaylistMapper(){}

    public static PlaylistResponseDto toPlaylistResponseDto(Playlist playlist){
        return PlaylistResponseDto.builder()
                .id(playlist.getId())
                .playlistName(playlist.getPlaylistName())
                .playlistImageUrl(playlist.getPlaylistImageUrl())
                .username(playlist.getUser().getUsername())
                .songs(playlist.getSongs())
                .build();
    }

    public static Playlist toPlaylist(PlaylistRequestDto playlistRequestDto){
        return Playlist.builder()
                .playlistName(playlistRequestDto.playlistName())
                .playlistImageUrl(playlistRequestDto.playlistImageUrl())
                .isPublic(playlistRequestDto.isPublic())
                .build();
    }

    public static Playlist toPlaylist(PlaylistRequestDto playlistRequestDto, User user){
        return Playlist.builder()
                .playlistName(playlistRequestDto.playlistName())
                .playlistImageUrl(playlistRequestDto.playlistImageUrl())
                .isPublic(true)
                .user(user)
                .build();
    }

    public static PlaylistNameByOwnerDto toPlaylistNameByOwnerDto(Playlist playlist){
        return PlaylistNameByOwnerDto.builder()
                .id(playlist.getId())
                .playlistName(playlist.getPlaylistName())
                .playlistImageUrl(playlist.getPlaylistImageUrl())
                .username(playlist.getUser().getUsername())
                .songs(playlist.getSongs().size())
                .build();
    }
}
