package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.services.album.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AlbumController {

    /*Size of pagination elements*/
    private static final Integer SIZE = 20;
    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumResponseDto> createAlbum(@RequestBody AlbumRequestDto albumRequestDto,
                                                        Authentication authentication){
        return ResponseEntity.ok(albumService.create(albumRequestDto, (User) authentication.getPrincipal()));
    }

    @PostMapping("/admin")
    public ResponseEntity<AlbumResponseDto> createAlbum(@RequestBody AlbumRequestDto albumRequestDto) {
        return ResponseEntity.ok(albumService.create(albumRequestDto));
    }

    @GetMapping
    public ResponseEntity<Page<AlbumResponseDto>> findRecentAlbums(@RequestParam(defaultValue = "0", name = "page") Integer page,
                                                                   @RequestParam(required = false) String albumName){
        if(albumName.isEmpty()) return ResponseEntity.ok(albumService.findAll(PageRequest.of(page, SIZE, Sort.by("createdAt").descending())));
        return ResponseEntity.ok(albumService.findByAlbumNameContaining(albumName, PageRequest.of(page, SIZE)));
    }

    @GetMapping("/artist")
    public ResponseEntity<Page<AlbumResponseDto>> findByArtistName(@RequestParam (defaultValue = "0", name = "page") Integer page,
                                                                   @RequestParam(required = true) String artistName){
        return ResponseEntity.ok(albumService.findByArtistNameContaining(artistName, PageRequest.of(page, SIZE)));
    }
}
