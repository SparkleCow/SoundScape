package com.sparklecow.soundscape.controllers;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.playlist.PlaylistNameByOwnerDto;
import com.sparklecow.soundscape.models.playlist.PlaylistRequestDto;
import com.sparklecow.soundscape.models.playlist.PlaylistResponseDto;
import com.sparklecow.soundscape.services.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private static final Integer SIZE = 20;
    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto,
                                                              Authentication authentication) {
        return ResponseEntity.ok(playlistService.createPlaylist(playlistRequestDto, (User) authentication.getPrincipal()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> findPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findPlaylistById(id));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<PlaylistResponseDto> findPlaylistByIdAsAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findPlaylistByIdAsAdmin(id));
    }

    @GetMapping("/complete-search")
    public ResponseEntity<PlaylistResponseDto> findPlaylistByName(@RequestParam String playlistName) {
        return ResponseEntity.ok(playlistService.findPlaylistByName(playlistName));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlaylistNameByOwnerDto>> findPlaylistByNameContaining(@RequestParam String playlistName,
                                                                            @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(playlistService.findPlaylistByNameContaining(playlistName, PageRequest.of(page, SIZE, Sort.by("playlistName"))));
    }

    @GetMapping("/admin/search")
    public ResponseEntity<PlaylistResponseDto> findPlaylistByNameAsAdmin(@RequestParam String playlistName) {
        return ResponseEntity.ok(playlistService.findPlaylistByNameAsAdmin(playlistName));
    }

    @GetMapping
    public ResponseEntity<Page<PlaylistNameByOwnerDto>> findPlaylists(
            @RequestParam(required = false) String playlistName,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") Integer page) {
        if (playlistName != null) {
            return ResponseEntity.ok(playlistService.findPlaylistByNameContaining(playlistName,
                    PageRequest.of(page, SIZE, Sort.by("playlistName"))));
        } else if (username != null) {
            return ResponseEntity.ok(playlistService.findPlaylistsByUsername(username,
                    PageRequest.of(page, SIZE, Sort.by("playlistName"))));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Page<PlaylistNameByOwnerDto>> findPlaylistsByUser(Authentication authentication,
                                                                            @RequestParam(required = false, defaultValue = "0") Integer page) {
        System.out.println("Entramooos");
        return ResponseEntity.ok(playlistService.findPlaylistsByUser(authentication,
                PageRequest.of(page, SIZE, Sort.by("playlistName"))));
    }

    @PatchMapping("/{id}/public-state")
    public ResponseEntity<Void> changePublicState(@PathVariable Long id, Authentication authentication) {
        playlistService.changePublicState(id, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId,
                                                  Authentication authentication) {
        playlistService.addSongToPlaylist(playlistId, songId, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId,
                                                       Authentication authentication) {
        playlistService.removeSongFromPlaylist(playlistId, songId, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylistAsAdmin(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylistAsAdmin(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id, Authentication authentication) {
        playlistService.deletePlaylist(id, (User) authentication.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
