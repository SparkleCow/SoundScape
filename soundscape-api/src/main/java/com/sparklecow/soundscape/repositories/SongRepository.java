package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
