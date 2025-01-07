package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumMapper {

    private final ArtistRepository artistRepository;

    public Album toAlbum(AlbumRequestDto albumRequestDto, Artist artist) {

        return Album.builder()
                .albumName(albumRequestDto.albumName())
                .coverImgUrl(albumRequestDto.coverImgUrl())
                .isExplicit(albumRequestDto.isExplicit())
                .releaseDate(albumRequestDto.releaseDate())
                .artists(List.of(artist))
                .build();
    }

    public Album toAlbum(AlbumRequestDto albumRequestDto){

        List<Artist> artistsList;

        if (albumRequestDto.artists().isEmpty()) {
            throw new RuntimeException("");
        }

        artistsList = albumRequestDto.artists().stream().map(artistName -> artistRepository.findByArtistNameIgnoreCase(artistName)
                        .orElseThrow(() -> new IllegalArgumentException("Artist not found: " + artistName)))
                        .toList();

        return Album.builder()
                .albumName(albumRequestDto.albumName())
                .coverImgUrl(albumRequestDto.coverImgUrl())
                .isExplicit(albumRequestDto.isExplicit())
                .releaseDate(albumRequestDto.releaseDate())
                .artists(artistsList)
                .build();
    }

    public AlbumResponseDto toAlbumResponseDto(Album album) {
        return AlbumResponseDto
                .builder()
                .id(album.getId())
                .albumName(album.getAlbumName())
                .coverImgUrl(album.getCoverImgUrl())
                .releaseDate(album.getReleaseDate())
                .isExplicit(album.getIsExplicit())
                .artists(album.getArtists().stream().map(Artist::getArtistName).toList())
                .songs(album.getSongs())
                .build();
    }
}
