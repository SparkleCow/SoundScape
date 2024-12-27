package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.services.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistResponseDto> findArtists(@RequestParam(required = false) String artistName){
        if(artistName == null || artistName.isEmpty()){
            return ResponseEntity.ok(artistService.findAll()); //TODO Pagination
        }
        return ResponseEntity.ok(artistService.findArtistByName(artistName));
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDto> createArtist(@RequestBody ArtistRequestDto artistRequestDto){
        artistService.create(artistRequestDto);
    }
}
