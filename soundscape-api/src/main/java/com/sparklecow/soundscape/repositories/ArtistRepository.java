package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.common.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Page<Artist> findByArtistNameContainingIgnoreCase(String artistName, Pageable pageable);

    Page<Artist> findByDebutYear(LocalDate debutYear, Pageable pageable);

    Page<Artist> findByGenresContaining(Genre genre, Pageable pageable);

    Optional<Artist> findByArtistNameIgnoreCase(String artistName);
}
