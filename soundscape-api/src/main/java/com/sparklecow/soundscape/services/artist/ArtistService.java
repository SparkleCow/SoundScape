package com.sparklecow.soundscape.services.artist;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.services.common.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ArtistService extends CrudService<ArtistRequestDto, ArtistResponseDto, ArtistUpdateDto> {

    ArtistResponseDto create(ArtistRequestDto artistRequestDto, User user);

    Page<ArtistResponseDto> findByArtistNameContaining(String artistName, Pageable pageable);

    Page<ArtistResponseDto> findArtistByGenre(String genre, Pageable pageable);

    Page<ArtistResponseDto> findArtistByDebutYear(LocalDate debutYear, Pageable pageable);

    ArtistResponseDto getArtistAccount(User user);

    Artist findArtistByName(String artistName);

    void addFollower(Long id, User user);

    void removeFollower(Long id, User user);

    void removeArtist(User user);
}
