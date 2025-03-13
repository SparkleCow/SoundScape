package com.sparklecow.soundscape.services.artist;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.exceptions.ArtistNotFoundException;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.models.common.Genre;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.mappers.ArtistMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    @Override
    public Page<ArtistResponseDto> findByArtistNameContaining(String artistName,  Pageable pageable) {
        // The map method used here belongs to the Page class from Spring Data JPA.
        // This is different from the map method of the Stream API.
        return artistRepository.findByArtistNameContainingIgnoreCase(artistName, pageable).map(ArtistMapper::toArtistResponseDto);
    }

    /*Upon receiving the genre value from artist controller, this method maps that value into a Genre enum and uses it in the repository*/
    @Override
    public Page<ArtistResponseDto> findArtistByGenre(String genre, Pageable pageable) {
        Genre genreEnum = Genre.valueOf(genre);
        return artistRepository.findByGenresContaining(genreEnum, pageable).map(ArtistMapper::toArtistResponseDto);
    }

    @Override
    public Page<ArtistResponseDto> findArtistByDebutYear(LocalDate debutYear, Pageable pageable) {
        return artistRepository.findByDebutYear(debutYear, pageable).map(ArtistMapper::toArtistResponseDto);
    }

    @Override
    public ArtistResponseDto getArtistAccount(User user) {
        return ArtistMapper.toArtistResponseDto(user.getArtist());
    }

    @Override
    public ArtistResponseDto findArtistById(Long id) {
        return ArtistMapper.toArtistResponseDto(artistRepository.findById(id).orElseThrow(() ->
                new ArtistNotFoundException("Artist with id: "+id+" not found")));
    }

    @Override
    public Artist findArtistByName(String artistName) {
        return artistRepository.findByArtistNameIgnoreCase(artistName).orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ArtistResponseDto create(ArtistRequestDto artistRequestDto) {
        Artist artist = ArtistMapper.toArtist(artistRequestDto);
        return ArtistMapper.toArtistResponseDto(artistRepository.save(artist));
    }

    @Override
    @Transactional
    public ArtistResponseDto create(ArtistRequestDto artistRequestDto, User user) {
        Artist artist = ArtistMapper.toArtist(artistRequestDto);
        artist.setUser(user);
        artistRepository.save(artist);
        user.setArtist(artist);
        userRepository.save(user);
        return ArtistMapper.toArtistResponseDto(artist);
    }

    @Override
    @Transactional
    public void addFollower(Long id, User user) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));

        if (artist.getFollowers().contains(user)) {
            throw new RuntimeException("User is already a follower of this artist");
        }

        artist.getFollowers().add(user);
        artistRepository.save(artist); // It isnt necesary save the user explicitly
    }

    @Override
    @Transactional
    public void removeFollower(Long id, User user) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));

        if (!artist.getFollowers().contains(user)) {
            throw new RuntimeException("User is not a follower of this artist");
        }

        Hibernate.initialize(artist.getFollowers());
        artist.getFollowers().remove(user);
        artistRepository.save(artist);
    }

    /*Unlike deleteArtist method, this method also deletes the artist, but will be used by the user who owns the artist.*/
    @Transactional
    @Override
    public void removeArtist(User user) {

        if (user.getArtist() == null) {
            throw new IllegalArgumentException("The user does not have an artist linked.");
        }

        Artist artist = user.getArtist();

        user.setArtist(null);
        userRepository.save(user);

        artistRepository.deleteById(artist.getId());
    }

    @Override
    public Page<ArtistResponseDto> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable).map(ArtistMapper::toArtistResponseDto);
    }

    @Override
    public ArtistResponseDto findById(Long id) {
        return null;
    }

    @Override
    public ArtistResponseDto updateById(ArtistUpdateDto artistUpdateDto, Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException("Artist with id: "+id+" not found"));
        Artist artistUpdated = artistRepository.save(ArtistMapper.updateArtist(artist, artistUpdateDto));
        return ArtistMapper.toArtistResponseDto(artistUpdated);
    }

    @Override
    public void deleteById(Long id) {

    }
}
