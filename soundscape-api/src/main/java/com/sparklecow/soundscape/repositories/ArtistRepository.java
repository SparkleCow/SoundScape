package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByArtistNameContaining (String artistName);

    Optional<Artist> findByArtistName(String artistName);
}
