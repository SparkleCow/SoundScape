package com.sparklecow.soundscape.services.songs;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.song.SongRequestDto;
import com.sparklecow.soundscape.models.song.SongResponseDto;
import com.sparklecow.soundscape.models.song.SongUpdateDto;
import com.sparklecow.soundscape.services.common.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface SongService extends CrudService<SongRequestDto, SongResponseDto, SongUpdateDto> {

    Page<SongResponseDto> findBySongName(String songName, Pageable pageable);
    Page<SongResponseDto> findByAlbumName(String albumName, Pageable pageable);
    Page<SongResponseDto> findByArtistName(String albumName, Pageable pageable);
    SongResponseDto create(SongRequestDto songRequestDto, User user);
    List<SongResponseDto> create(Collection<SongRequestDto> songs);
    SongResponseDto updateSongAsUser(User user, SongUpdateDto songUpdateDto, Long id);
}
