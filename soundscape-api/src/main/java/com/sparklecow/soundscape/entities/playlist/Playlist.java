package com.sparklecow.soundscape.entities.playlist;

import com.sparklecow.soundscape.entities.song.Song;
import com.sparklecow.soundscape.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//This allows to have creation and update information for this class
@EntityListeners(AuditingEntityListener.class)
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, insertable = false)
    private LocalDateTime lastModifiedAt;

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long lastModifiedBy;

    private boolean isPublic = false;

    @Column(length = 50)
    private String playlistName;

    private String playlistImageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "playlist_song",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();
}
