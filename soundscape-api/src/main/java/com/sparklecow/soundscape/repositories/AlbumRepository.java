package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.album.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByAlbumNameContainingIgnoreCase(String artistName, Pageable pageable);
    Page<Album> findByArtistsArtistNameContainingIgnoreCase(String artistName, Pageable pageable);
    Optional<Album> findByAlbumName(String albumName);
}
