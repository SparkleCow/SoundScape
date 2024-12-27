package com.sparklecow.soundscape.services;

import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.models.common.Genre;
import java.time.LocalDate;
import java.util.List;

public interface ArtistService extends CrudService<ArtistRequestDto, ArtistResponseDto, ArtistUpdateDto> {

    List<ArtistResponseDto> findArtistByName(String artistName);

    List<ArtistResponseDto> findArtistByGenre(Genre genre);

    List<ArtistResponseDto> findArtistByDebutYear(LocalDate debutYear);
}
