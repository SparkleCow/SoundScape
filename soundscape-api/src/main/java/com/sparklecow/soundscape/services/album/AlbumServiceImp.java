package com.sparklecow.soundscape.services.album;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.exceptions.AlbumNotFoundException;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.models.album.AlbumUpdateDto;
import com.sparklecow.soundscape.repositories.AlbumRepository;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import com.sparklecow.soundscape.services.mappers.AlbumMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImp implements AlbumService{

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;

    /*This method allows to create an album to the logged user. It will use the artist linked to the user*/
    @Override
    @Transactional
    public AlbumResponseDto create(AlbumRequestDto albumRequestDto, User user) {
        Artist artist = user.getArtist();
        Album album = albumMapper.toAlbum(albumRequestDto, artist);
        artist.getAlbums().add(album);
        album.getArtists().add(artist);
        albumRepository.save(album);
        artistRepository.save(artist);
        return albumMapper.toAlbumResponseDto(album);
    }

    /*This method allows to create an album to the logged admin. It won't use the administrator account
    * for linked it, it will require a list of strings in albumRequestDto data that matches with the artist names in database */
    @Override
    public AlbumResponseDto create(AlbumRequestDto albumRequestDto) {
        Album album = albumRepository.save(albumMapper.toAlbum(albumRequestDto));
        return albumMapper.toAlbumResponseDto(album);
    }

    @Override
    public Page<AlbumResponseDto> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable).map(albumMapper::toAlbumResponseDto);
    }

    @Override
    public Page<AlbumResponseDto> findByAlbumNameContaining(String albumName, Pageable pageable) {
        return albumRepository.findByAlbumNameContainingIgnoreCase(albumName, pageable).map(albumMapper::toAlbumResponseDto);
    }

    @Override
    public Page<AlbumResponseDto> findByArtistNameContaining(String artistName, Pageable pageable) {
        return albumRepository.findByArtistsArtistNameContainingIgnoreCase(artistName, pageable).map(albumMapper::toAlbumResponseDto);
    }

    @Override
    public AlbumResponseDto findById(Long id) {
        return albumMapper.toAlbumResponseDto(albumRepository.findById(id).
                orElseThrow(() -> new AlbumNotFoundException("Album with id: "+id+" not found")));
    }

    @Override
    public AlbumResponseDto updateById(AlbumUpdateDto albumUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + id + " not found"));
        albumRepository.delete(album);
    }
}
