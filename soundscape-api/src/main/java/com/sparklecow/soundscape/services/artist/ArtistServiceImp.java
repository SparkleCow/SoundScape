package com.sparklecow.soundscape.services.artist;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.models.common.Genre;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import com.sparklecow.soundscape.repositories.UserRepository;
import com.sparklecow.soundscape.services.mappers.ArtistMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistServiceImp implements ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    @Override
    public List<ArtistResponseDto> findByArtistNameContaining(String artistName) {
        return artistRepository.findByArtistNameContaining(artistName).stream().map(ArtistMapper::toArtistResponseDto).toList();
    }

    @Override
    public Artist findArtistByName(String artistName) {
        return artistRepository.findByArtistName(artistName).orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ArtistResponseDto> findArtistByGenre(Genre genre) {
        return List.of();
    }

    @Override
    public List<ArtistResponseDto> findArtistByDebutYear(LocalDate debutYear) {
        return List.of();
    }

    @Override
    public ArtistResponseDto create(ArtistRequestDto artistRequestDto) {
        Artist artist = ArtistMapper.toArtist(artistRequestDto);
        return ArtistMapper.toArtistResponseDto(artistRepository.save(artist));
    }

    @Override
    @Transactional
    public ArtistResponseDto create(ArtistRequestDto artistRequestDto, User user) {
        Artist artist = artistRepository.save(ArtistMapper.toArtist(artistRequestDto));
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
    public List<ArtistResponseDto> findAll() {
        return artistRepository.findAll().stream().map(ArtistMapper::toArtistResponseDto).toList();
    }

    @Override
    public ArtistResponseDto findById(Long id) {
        return null;
    }

    @Override
    public ArtistResponseDto updateById(ArtistUpdateDto artistUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
