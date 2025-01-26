package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.song.Song;
import com.sparklecow.soundscape.models.song.SongRequestDto;
import com.sparklecow.soundscape.models.song.SongResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SongMapper {

    private SongMapper(){}

    public static Song toSong(SongRequestDto songRequestDto, Album album) {
        List<Artist> artistsCopy = new ArrayList<>(album.getArtists());
        return Song.builder()
                .songName(songRequestDto.songName())
                .releaseDate(songRequestDto.releaseDate())
                .duration(songRequestDto.duration())
                .isExplicit(songRequestDto.isExplicit())
                .lyrics(songRequestDto.lyrics())
                .producer(songRequestDto.producer())
                .streamingUrl(songRequestDto.streamingUrl())
                .album(album)
                .artists(artistsCopy)
                .build();
    }

    public static SongResponseDto toSongResponseDto(Song song) {
        return SongResponseDto
                .builder()
                .id(song.getId())
                .songName(song.getSongName())
                .duration(song.getDuration())
                .streamingUrl(song.getStreamingUrl())
                .album(song.getAlbum().getAlbumName())
                .isExplicit(song.getIsExplicit())
                .lyrics(song.getLyrics())
                .releaseDate(song.getReleaseDate())
                .producer(song.getProducer())
                .artists(song.getArtists().stream()
                        .map(Artist::getArtistName)
                        .collect(Collectors.toList()))
                .build();
    }
}
