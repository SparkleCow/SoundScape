package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.artist.ArtistRequestDto;
import com.sparklecow.soundscape.models.artist.ArtistResponseDto;
import com.sparklecow.soundscape.services.artist.ArtistService;
import com.sparklecow.soundscape.services.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final UserService userService;

    /*This allows find artist based on their name. It will return a list of matches*/
    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> findArtists(@RequestParam(required = false, name = "name") String artistName){
        if(artistName == null || artistName.isEmpty()){
            return ResponseEntity.ok(artistService.findAll());
        }
        return ResponseEntity.ok(artistService.findByArtistNameContaining(artistName));
    }

    /*This method allows to create an artist based on the authenticated user. First it creates the artist, then use the
    * authentication object and set the artist in our authenticated user. Finally, it returns an artist DTO.*/
    @PostMapping
    public ResponseEntity<ArtistResponseDto> createArtist(@RequestBody ArtistRequestDto artistRequestDto,
                                                          Authentication authentication){
        User user = (User)authentication.getPrincipal();
        ArtistResponseDto artistResponseDto = artistService.create(artistRequestDto, user);
        Artist artist = artistService.findArtistByName(artistRequestDto.artistName());
        user.setArtist(artist);
        return ResponseEntity.ok(artistResponseDto);
    }

    /*This method allows to create artist without set it to a user. This method is useful for administrators and moderators.*/
    @PostMapping("/admin")
    public ResponseEntity<ArtistResponseDto> createArtist(@RequestBody ArtistRequestDto artistRequestDto) throws MessagingException {
        ArtistResponseDto artistResponseDto = artistService.create(artistRequestDto);
        return ResponseEntity.ok(artistResponseDto);
    }

    @PostMapping("/{id}/followers")
    public ResponseEntity<Void> addFollower(@PathVariable Long id,
                                            Authentication authentication){
        artistService.addFollower(id, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/followers")
    public ResponseEntity<Void> removeFollower(@PathVariable Long id,
                                            Authentication authentication){
        artistService.removeFollower(id, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> removeArtist(Authentication authentication){
        artistService.removeArtist((User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
