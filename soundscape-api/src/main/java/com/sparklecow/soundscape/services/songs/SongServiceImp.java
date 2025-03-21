package com.sparklecow.soundscape.services.songs;

import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.song.Song;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.exceptions.AlbumNotFoundException;
import com.sparklecow.soundscape.exceptions.IllegalOperationException;
import com.sparklecow.soundscape.exceptions.OperationNotPermittedException;
import com.sparklecow.soundscape.exceptions.SongNotFoundException;
import com.sparklecow.soundscape.models.song.SongRequestDto;
import com.sparklecow.soundscape.models.song.SongResponseDto;
import com.sparklecow.soundscape.models.song.SongUpdateDto;
import com.sparklecow.soundscape.repositories.AlbumRepository;
import com.sparklecow.soundscape.repositories.SongRepository;
import com.sparklecow.soundscape.services.mappers.SongMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImp implements SongService{

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    /**This method allows to create a song and link it to the logged artist. If the requested artist is not linked to
    * the logged user, it will return an OperationNotPermittedException since this only allows to create a song for the logged user.*/
    @Override
    @Transactional
    public SongResponseDto create(SongRequestDto songRequestDto, User user) {
        Album album = albumRepository.findByAlbumName(songRequestDto.album()).orElseThrow(
                () -> new AlbumNotFoundException("Album not found with name: "+songRequestDto.album()));

        if(album.getArtists().stream().noneMatch(x -> x.getArtistName().equals(user.getArtist().getArtistName()))){
            throw new OperationNotPermittedException("This user can not create a song to other artist. It only could create a song for its artist account");
        }
        Song song = SongMapper.toSong(songRequestDto, album);
        album.getSongs().add(song);
        songRepository.save(song);
        return SongMapper.toSongResponseDto(song);
    }

    /**This method only requires the sonRequestDto and the album list since it will be used by administrators, so
    * this songs won`t be linked to the administrator logged.*/
    @Override
    @Transactional
    public SongResponseDto create(SongRequestDto songRequestDto) {
        Album album = albumRepository.findByAlbumName(songRequestDto.album()).orElseThrow(
                () -> new AlbumNotFoundException("Album not found with name: "+songRequestDto.album()));

        Song song = SongMapper.toSong(songRequestDto, album);
        album.getSongs().add(song);
        songRepository.save(song);
        return SongMapper.toSongResponseDto(song);
    }

    /**This method processes a collection of SongRequestDto objects, associates each song with its corresponding album,
    * saves the songs to the database, and converts them into SongResponseDto for the response.
    * The method supports pagination through the Pageable parameter.*/
    @Override
    @Transactional
    public List<SongResponseDto> create(Collection<SongRequestDto> songs) {
        return songs.stream().map(x -> {
            Album album = albumRepository.findByAlbumName(x.album()).orElseThrow(
                    () -> new AlbumNotFoundException("Album not found with name: " + x.album()));

            Song song = SongMapper.toSong(x, album);
            songRepository.save(song);
            return SongMapper.toSongResponseDto(song);
        }).toList();
    }

    @Override
    public Page<SongResponseDto> findBySongName(String songName, Pageable pageable) {
        return songRepository.findBySongNameContainingIgnoreCase(songName, pageable).map(SongMapper::toSongResponseDto);
    }

    @Override
    public Page<SongResponseDto> findByAlbumName(String albumName, Pageable pageable) {
        return songRepository.findByAlbumAlbumNameContainingIgnoreCase(albumName, pageable).map(SongMapper::toSongResponseDto);
    }

    @Override
    public Page<SongResponseDto> findByArtistName(String albumName, Pageable pageable) {
        return songRepository.findByArtistsArtistNameContainingIgnoreCase(albumName, pageable).map(SongMapper::toSongResponseDto);
    }

    @Override
    public Page<SongResponseDto> findAll(Pageable pageable) {
        return songRepository.findAll(pageable).map(SongMapper::toSongResponseDto);
    }

    @Override
    public SongResponseDto findById(Long id) {
        return SongMapper.toSongResponseDto(songRepository.findById(id)
                .orElseThrow(()-> new SongNotFoundException("Song with id: "+id+" not found")));
    }

    @Override
    public SongResponseDto updateById(SongUpdateDto songUpdateDto, Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new SongNotFoundException("Song with id: "+id+" not found"));
        Song songUpdated = SongMapper.updateSongFromDto(song, songUpdateDto);
        return SongMapper.toSongResponseDto(songUpdated);
    }


    @Override
    public SongResponseDto updateSongAsUser(User user, SongUpdateDto songUpdateDto, Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(()-> new SongNotFoundException("Song with id: "+id+" not found"));
        String userArtistName = user.getArtist().getArtistName();

        boolean isUserArtistInAlbum = song.getAlbum().getArtists()
                .stream()
                .anyMatch(artist -> artist.getArtistName().equals(userArtistName));

        if (!isUserArtistInAlbum) {
            throw new IllegalOperationException("You are not authorized to update this song.");
        }
        return SongMapper.toSongResponseDto(songRepository.save(SongMapper.updateSongFromDto(song, songUpdateDto)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: "+id+" not found"));

        if (song.getAlbum() != null) {
            song.getAlbum().getSongs().remove(song);
            song.setAlbum(null);
        }

        songRepository.delete(song);
    }
}
