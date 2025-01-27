package com.sparklecow.soundscape.services.playlist;

import com.sparklecow.soundscape.entities.playlist.Playlist;
import com.sparklecow.soundscape.entities.song.Song;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.exceptions.*;
import com.sparklecow.soundscape.models.playlist.PlaylistNameByOwnerDto;
import com.sparklecow.soundscape.models.playlist.PlaylistRequestDto;
import com.sparklecow.soundscape.models.playlist.PlaylistResponseDto;
import com.sparklecow.soundscape.models.playlist.PlaylistUpdateDto;
import com.sparklecow.soundscape.repositories.PlaylistRepository;
import com.sparklecow.soundscape.repositories.SongRepository;
import com.sparklecow.soundscape.services.mappers.PlaylistMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImp implements PlaylistService{

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    @Override
    public PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto, User user) {
        Playlist playlist = PlaylistMapper.toPlaylist(playlistRequestDto, user);
        return PlaylistMapper.toPlaylistResponseDto(playlistRepository.save(playlist));
    }

    /**
     * This method is executed by administrators to create playlists.
     * Playlists created through this method are public and not associated with any specific user.
     * It converts the incoming PlaylistRequestDto to a Playlist entity, saves it,
     * and then maps the saved entity to a PlaylistResponseDto for the response.
     */
    @Override
    public PlaylistResponseDto createPlaylist(PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = PlaylistMapper.toPlaylist(playlistRequestDto);
        return PlaylistMapper.toPlaylistResponseDto(playlistRepository.save(playlist));
    }

    @Override
    public PlaylistResponseDto findPlaylistById(Long id) {
        return PlaylistMapper.toPlaylistResponseDto(playlistRepository.findById(id)
                .orElseThrow( ()-> new PlaylistNotFoundException("Playlist with id: "+id+" not found")));
    }

    @Override
    public PlaylistResponseDto findPlaylistByName(String playlistName) {
        return PlaylistMapper.toPlaylistResponseDto(playlistRepository.findByPlaylistName(playlistName)
                .orElseThrow( ()-> new PlaylistNotFoundException("Playlist with name: "+playlistName+" not found")));
    }

    @Override
    public Page<PlaylistNameByOwnerDto> findPlaylistByNameContaining(String playlistName, Pageable pageable) {
        return playlistRepository.findByPlaylistNameContainingIgnoreCase(playlistName, pageable).map(PlaylistMapper::toPlaylistNameByOwnerDto);
    }

    @Override
    public Page<PlaylistNameByOwnerDto> findPlaylistsByUsername(String username, Pageable pageable) {
        return playlistRepository.findByUserUsernameContainingIgnoreCase(username, pageable).map(PlaylistMapper::toPlaylistNameByOwnerDto);
    }

    @Override
    public Page<PlaylistNameByOwnerDto> findPlayListsByUser(User user, Pageable pageable) {
        return playlistRepository.findByUserUsernameContainingIgnoreCase(user.getUsername(), pageable).map(PlaylistMapper::toPlaylistNameByOwnerDto);
    }

    /**
     * Changes the public state of a playlist identified by its ID.
     *
     * Retrieve the playlist by ID; throw an exception if not found.
     * Check if the provided user is the owner of the playlist; throw an exception if not.
     * Toggle the public state of the playlist (true -> false, false -> true).
     * Save the updated playlist back to the repository.
     *
     * Exceptions:
     * - PlaylistNotFoundException: Thrown if the playlist doesn't exist.
     * - OperationNotPermittedException: Thrown if the user is not the playlist owner.
     */
    @Override
    @Transactional
    public void changePublicState(Long id, User user) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow( ()-> new PlaylistNotFoundException("Playlist with id: "+id+" not found"));
        if(!playlist.getUser().getUsername().equals(user.getUsername())){
            throw new OperationNotPermittedException("You are not the owner of this playlist");
        }
        playlist.setPublic(!playlist.isPublic());
        playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public Playlist addSongToPlaylist(Long playlistId, Long songId, User user) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow( ()-> new PlaylistNotFoundException("Playlist with id: "+playlistId+" not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(()-> new SongNotFoundException("Song with id: "+songId+" not found"));

        if(!playlist.getUser().getUsername().equals(user.getUsername())){
            throw new OperationNotPermittedException("You are not the owner of this playlist");
        }

        if (playlist.getSongs().contains(song)) {
            throw new SongAlreadyInPlaylistException("The song is already in the playlist");
        }

        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public Playlist removeSongFromPlaylist(Long playlistId, Long songId, User user) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " +playlistId+ " not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " +songId+ " not found"));

        if (!playlist.getUser().getUsername().equals(user.getUsername())) {
            throw new OperationNotPermittedException("You are not the owner of this playlist");
        }

        if (!playlist.getSongs().contains(song)) {
            throw new SongNotInPlaylistException("The song is not in the playlist");
        }

        playlist.getSongs().remove(song);
        return playlistRepository.save(playlist);
    }

    /**
     * Adds a song to the specified playlist.
     * This method is designed for administrators and does not verify playlist ownership.
     * @throws PlaylistNotFoundException if the playlist is not found
     * @throws SongNotFoundException if the song is not found
     * @throws SongNotInPlaylistException if the song is already in the playlist
     */
    @Override
    @Transactional
    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " +playlistId+ " not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " +songId+ " not found"));

        if (!playlist.getSongs().contains(song)) {
            throw new SongNotInPlaylistException("The song is not in the playlist");
        }

        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }

    /**
     * Removes a song from the specified playlist.
     * This method is designed for administrators and does not verify playlist ownership.
     * @throws PlaylistNotFoundException if the playlist is not found
     * @throws SongNotFoundException if the song is not found
     * @throws SongNotInPlaylistException if the song is not in the playlist
     */
    @Override
    @Transactional
    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist with id: " +playlistId+ " not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " +songId+ " not found"));

        if (!playlist.getSongs().contains(song)) {
            throw new SongNotInPlaylistException("The song is not in the playlist");
        }

        playlist.getSongs().remove(song);
        return playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(Long id, User user) {

    }

    @Override
    public Playlist updatePlaylist(Long id, PlaylistUpdateDto playlistUpdateDto) {
        return null;
    }
}
