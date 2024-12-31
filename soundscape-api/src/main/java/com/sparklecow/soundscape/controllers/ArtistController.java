package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.services.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> findArtists(@RequestParam(required = false) String artistName){
        if(artistName == null || artistName.isEmpty()){
            return ResponseEntity.ok(artistService.findAll()); //TODO Pagination
        }
        return ResponseEntity.ok(artistService.findArtistByName(artistName));
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDto> createArtist(@RequestBody ArtistRequestDto artistRequestDto){
        return ResponseEntity.ok(artistService.create(artistRequestDto));
    }
}
