package com.sparklecow.soundscape.entities.album;

import com.sparklecow.soundscape.entities.artist.Artist;
import com.sparklecow.soundscape.entities.song.Song;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;

    @Column(nullable = false, length = 30)
    private String albumName;

    private String coverImgUrl;

    private LocalDate releaseDate;

    private Boolean isExplicit;

    @ManyToMany(mappedBy = "albums")
    private List<Artist> artists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "album_songs",
            joinColumns = @JoinColumn( name = "album_id"),
            inverseJoinColumns = @JoinColumn( name = "song_id")
    )
    private List<Song> songs = new ArrayList<>();
}
