package com.sparklecow.soundscape.services.playlist;

import com.sparklecow.soundscape.entities.playlist.Playlist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.playlist.PlaylistNameByOwnerDto;
import com.sparklecow.soundscape.models.playlist.PlaylistRequestDto;
import com.sparklecow.soundscape.models.playlist.PlaylistResponseDto;
import com.sparklecow.soundscape.models.playlist.PlaylistUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaylistService {

    PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto, User user);
    PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto);

    PlaylistResponseDto findPlaylistById(Long id);
    PlaylistResponseDto findPlaylistByName(String playlistName);
    Page<PlaylistNameByOwnerDto> findPlaylistByNameContaining(String playlistName, Pageable pageable);
    Page<PlaylistNameByOwnerDto> findPlaylistsByUsername(String username, Pageable pageable);
    Page<PlaylistNameByOwnerDto> findPlayListsByUser(User user, Pageable pageable);

    void changePublicState(Long id, User user);

    Playlist addSongToPlaylist(Long playlistId, Long songId, User user);
    Playlist removeSongFromPlaylist(Long playlistId, Long songId, User user);

    Playlist addSongToPlaylist(Long playlistId, Long songId);
    Playlist removeSongFromPlaylist(Long playlistId, Long songId);

    void deletePlaylist(Long id, User user);

    Playlist updatePlaylist(Long id, PlaylistUpdateDto playlistUpdateDto);
}
