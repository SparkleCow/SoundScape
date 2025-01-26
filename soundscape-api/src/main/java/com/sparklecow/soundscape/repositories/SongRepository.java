package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> findBySongNameContainingIgnoreCase(String songName, Pageable pageable);
    Page<Song> findByAlbumAlbumNameContainingIgnoreCase(String albumName, Pageable pageable);
    Page<Song> findByArtistsArtistNameContainingIgnoreCase(String albumName, Pageable pageable);
}
