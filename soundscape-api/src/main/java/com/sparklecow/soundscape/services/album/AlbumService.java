package com.sparklecow.soundscape.services.album;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.models.album.AlbumUpdateDto;
import com.sparklecow.soundscape.services.common.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AlbumService extends CrudService<AlbumRequestDto, AlbumResponseDto, AlbumUpdateDto> {

    AlbumResponseDto create(AlbumRequestDto albumRequestDto, User user);

    Page<AlbumResponseDto> findByAlbumNameContaining(String albumName, Pageable pageable);

    Page<AlbumResponseDto> findByArtistNameContaining(String artistName, Pageable pageable);
}
