package com.sparklecow.soundscape.services.playlist;

import com.sparklecow.soundscape.entities.playlist.Playlist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.playlist.PlaylistNameByOwnerDto;
import com.sparklecow.soundscape.models.playlist.PlaylistRequestDto;
import com.sparklecow.soundscape.models.playlist.PlaylistResponseDto;
import com.sparklecow.soundscape.models.playlist.PlaylistUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PlaylistService {

    PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto, User user);

    PlaylistResponseDto findPlaylistById(Long id);
    PlaylistResponseDto findPlaylistByIdAsAdmin(Long id);

    PlaylistResponseDto findPlaylistByName(String playlistName);
    PlaylistResponseDto findPlaylistByNameAsAdmin(String playlistName);

    Page<PlaylistNameByOwnerDto> findPlaylistByNameContaining(String playlistName, Pageable pageable);
    Page<PlaylistNameByOwnerDto> findPlaylistByNameContainingAsAdmin(String playlistName, Pageable pageable);

    Page<PlaylistNameByOwnerDto> findPlaylistsByUsername(String username, Pageable pageable);
    Page<PlaylistNameByOwnerDto> findPlaylistsByUsernameAsAdmin(String username, Pageable pageable);

    Page<PlaylistNameByOwnerDto> findPlaylistsByUser(Authentication authentication, Pageable pageable);

    void changePublicState(Long id, User user);

    void addSongToPlaylist(Long playlistId, Long songId, User user);
    void removeSongFromPlaylist(Long playlistId, Long songId, User user);

    void addSongToPlaylist(Long playlistId, Long songId);
    void removeSongFromPlaylist(Long playlistId, Long songId);

    void deletePlaylist(Long id, User user);

    Playlist updatePlaylist(Long id, PlaylistUpdateDto playlistUpdateDto);
}
