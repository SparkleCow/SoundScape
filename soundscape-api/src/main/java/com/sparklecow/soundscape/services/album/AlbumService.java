package com.sparklecow.soundscape.services.album;


import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.models.album.AlbumUpdateDto;
import com.sparklecow.soundscape.services.common.CrudService;

import java.util.List;

public interface AlbumService extends CrudService<AlbumRequestDto, AlbumResponseDto, AlbumUpdateDto> {

    AlbumResponseDto create(AlbumRequestDto albumRequestDto, String artistName);

    List<AlbumResponseDto> findRecentAlbums();

    List<AlbumResponseDto> findByArtistNameContaining(String artistName);
}
