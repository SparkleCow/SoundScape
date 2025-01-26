package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.song.SongRequestDto;
import com.sparklecow.soundscape.models.song.SongResponseDto;
import com.sparklecow.soundscape.services.songs.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/song")
@CrossOrigin("*")
public class SongController {

    private static final Integer SIZE = 20;
    private final SongService songService;

    @PostMapping
    public ResponseEntity<SongResponseDto> createSong(@RequestBody SongRequestDto songRequestDto,
                                                            Authentication authentication){
        return ResponseEntity.ok(songService.create(songRequestDto, (User) authentication.getPrincipal()));
    }

    @PostMapping("/admin")
    public ResponseEntity<SongResponseDto> createSong(@RequestBody SongRequestDto songRequestDto) {
        return ResponseEntity.ok(songService.create(songRequestDto));
    }

    @PostMapping("/admin/bulk")
    public ResponseEntity<List<SongResponseDto>> createSongs(@RequestBody List<SongRequestDto> songs){
        return ResponseEntity.ok(songService.create(songs));
    }

    @GetMapping
    public ResponseEntity<Page<SongResponseDto>> findSongs(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(required = false) String songName){
        if(songName!=null && !songName.isEmpty()){
            return ResponseEntity.ok(songService.findBySongName(songName, PageRequest.of(page, SIZE, Sort.by("createdAt").descending())));
        }
        return ResponseEntity.ok(songService.findAll(PageRequest.of(page, SIZE, Sort.by("createdAt").descending())));
    }

    @GetMapping("/album")
    public ResponseEntity<Page<SongResponseDto>> findByAlbumName(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(required = true) String albumName){
        return ResponseEntity.ok(songService.findByAlbumName(albumName, PageRequest.of(page, SIZE, Sort.by("createdAt").descending())));
    }

    @GetMapping("/artist")
    public ResponseEntity<Page<SongResponseDto>> findByArtistName(@RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(required = true) String artistName){
        return ResponseEntity.ok(songService.findByArtistName(artistName, PageRequest.of(page, SIZE, Sort.by("createdAt").descending())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable Long id){
        songService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
