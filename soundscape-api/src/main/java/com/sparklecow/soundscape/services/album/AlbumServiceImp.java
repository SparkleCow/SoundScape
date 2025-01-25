package com.sparklecow.soundscape.services.album;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.models.album.AlbumUpdateDto;
import com.sparklecow.soundscape.repositories.AlbumRepository;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import com.sparklecow.soundscape.services.mappers.AlbumMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public AlbumResponseDto create(AlbumRequestDto albumRequestDto, String artistName) {

        Artist artist = artistRepository.findByArtistNameContainingIgnoreCase(artistName, PageRequest.of(1,1))
                .stream().findFirst().orElseThrow(() -> new RuntimeException("Artist not found"));

        Album album = albumMapper.toAlbum(albumRequestDto, artist);
        albumRepository.save(album);

        artist.getAlbums().add(album);
        artistRepository.save(artist);

        return albumMapper.toAlbumResponseDto(album);
    }

    /*This method allows to create an album to the logged admin. It won't use the administrator account
    * for linked it, it will require a list of strings that matches with the artist names in database.*/
    @Override
    public AlbumResponseDto create(AlbumRequestDto albumRequestDto) {
        Album album = albumRepository.save(albumMapper.toAlbum(albumRequestDto));
        return albumMapper.toAlbumResponseDto(album);
    }

    @Override
    public List<AlbumResponseDto> findRecentAlbums() {
        return albumRepository.findAllByCreatedAtDesc(PageRequest.of(1,1)).stream().map(albumMapper::toAlbumResponseDto).toList();
    }

    @Override
    public List<AlbumResponseDto> findByArtistNameContaining(String artistName) {
        return albumRepository.findByArtistsArtistNameContainingIgnoreCase(artistName, PageRequest.of(1,1))
                .stream().map(albumMapper::toAlbumResponseDto).toList();
    }

    @Override
    public Page<AlbumResponseDto> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable).map(albumMapper::toAlbumResponseDto);
    }

    @Override
    public AlbumResponseDto findById(Long id) {
        return null;
    }

    @Override
    public AlbumResponseDto updateById(AlbumUpdateDto albumUpdateDto, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
