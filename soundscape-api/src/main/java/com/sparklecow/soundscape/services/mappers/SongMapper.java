package com.sparklecow.soundscape.services.mappers;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.song.Song;
import com.sparklecow.soundscape.models.song.SongRequestDto;
import com.sparklecow.soundscape.models.song.SongResponseDto;
import com.sparklecow.soundscape.models.song.SongUpdateDto;

import java.util.ArrayList;
import java.util.List;

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
                        .toList())
                .albumImage(song.getAlbum().getCoverImgUrl())
                .build();
    }

    public static Song updateSongFromDto(Song song, SongUpdateDto songDto) {
        if (songDto.songName() != null) {
            song.setSongName(songDto.songName());
        }
        if (songDto.releaseDate() != null) {
            song.setReleaseDate(songDto.releaseDate());
        }
        if (songDto.duration() != null) {
            song.setDuration(songDto.duration());
        }
        if (songDto.isExplicit() != null) {
            song.setIsExplicit(songDto.isExplicit());
        }
        if (songDto.lyrics() != null) {
            song.setLyrics(songDto.lyrics());
        }
        if (songDto.producer() != null) {
            song.setProducer(songDto.producer());
        }
        if (songDto.streamingUrl() != null) {
            song.setStreamingUrl(songDto.streamingUrl());
        }
        return song;
    }
}
