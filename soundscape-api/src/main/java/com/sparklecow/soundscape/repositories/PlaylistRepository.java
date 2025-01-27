package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.playlist.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByUserUsernameContainingIgnoreCase(String username, Pageable pageable);
    Page<Playlist> findByPlaylistNameContainingIgnoreCase(String username, Pageable pageable);
    Optional<Playlist> findByPlaylistName(String username);
}
