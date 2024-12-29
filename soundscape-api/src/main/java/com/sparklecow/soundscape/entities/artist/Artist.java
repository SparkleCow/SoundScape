package com.sparklecow.soundscape.entities.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparklecow.soundscape.entities.album.Album;
import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.common.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//This allows to have creation and update information for this class
@EntityListeners(AuditingEntityListener.class)
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "artist_name", length = 30, nullable = false, unique = true)
    private String artistName;

    private String description;

    private String profileImageUrl;

    private String bannerImageUrl;

    private LocalDate debutYear;

    private Boolean isVerified;

    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "artist_social_media",
            joinColumns = @JoinColumn(name = "artist_id")
    )
    @Column(name = "url")
    private Map<String, String> socialMediaUrls = new HashMap<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "artist_genres",
            joinColumns = @JoinColumn(name = "artist_id")
    )
    @Column(name = "genre")
    private Set<Genre> genres = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "artist_album",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private List<Album> albums = new ArrayList<>();

    private String websiteUrl;

    public Long getFollowersCount() {
        if (followers == null || followers.isEmpty()) {
            return 0L;
        }
        return (long) followers.size();
    }
}
