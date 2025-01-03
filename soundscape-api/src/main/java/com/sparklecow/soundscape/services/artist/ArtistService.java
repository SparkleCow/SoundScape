package com.sparklecow.soundscape.services.artist;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.models.common.Genre;
import com.sparklecow.soundscape.services.common.CrudService;

import java.time.LocalDate;
import java.util.List;

public interface ArtistService extends CrudService<ArtistRequestDto, ArtistResponseDto, ArtistUpdateDto> {

    List<ArtistResponseDto> findByArtistNameContaining(String artistName);

    Artist findArtistByName(String artistName);

    List<ArtistResponseDto> findArtistByGenre(Genre genre);

    List<ArtistResponseDto> findArtistByDebutYear(LocalDate debutYear);

    ArtistResponseDto create(ArtistRequestDto artistRequestDto, User user);

    void addFollower(Long id, User user);
}
