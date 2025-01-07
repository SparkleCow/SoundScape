package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.album.AlbumRequestDto;
import com.sparklecow.soundscape.models.album.AlbumResponseDto;
import com.sparklecow.soundscape.services.album.AlbumService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumResponseDto> createAlbum(@RequestBody AlbumRequestDto albumRequestDto,
                                                        Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(albumService.create(albumRequestDto, user.getArtist().getArtistName()));
    }

    @PostMapping("/admin")
    public ResponseEntity<AlbumResponseDto> createAlbum(@RequestBody AlbumRequestDto albumRequestDto) throws MessagingException {
        return ResponseEntity.ok(albumService.create(albumRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDto>> findRecentAlbums(){
        return ResponseEntity.ok(albumService.findRecentAlbums());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumResponseDto>> findByArtistName(@RequestParam(required = true, name = "name") String artistName){
        return ResponseEntity.ok(albumService.findByArtistNameContaining(artistName));
    }
}
