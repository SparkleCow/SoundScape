package com.sparklecow.soundscape.services;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.models.artist.ArtistUpdateDto;
import com.sparklecow.soundscape.models.common.Genre;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import com.sparklecow.soundscape.services.mappers.ArtistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImp implements ArtistService{

    private final ArtistRepository artistRepository;

    @Override
    public List<ArtistResponseDto> findArtistByName(String artistName) {
        return artistRepository.findByArtistNameContaining(artistName).stream().map(ArtistMapper::toArtistResponseDto).toList();
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
    public List<ArtistResponseDto> findAll() {
        return List.of();
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
