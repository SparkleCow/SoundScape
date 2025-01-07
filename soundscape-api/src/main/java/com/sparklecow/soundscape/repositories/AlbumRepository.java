package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findTop20ByOrderByCreatedAtDesc();

    List<Album> findByArtistsArtistNameContainingIgnoreCase(String artistName);
}
