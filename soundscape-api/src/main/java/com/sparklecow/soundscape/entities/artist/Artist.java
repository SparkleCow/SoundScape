package com.sparklecow.soundscape.entities.artist;

import com.sparklecow.soundscape.models.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_name", length = 30, nullable = false, unique = true)
    private String artistName;

    private String description;

    private String profileImageUrl;

    private String bannerImageUrl;

    private LocalDate debutYear;

    private Boolean isVerified;

    private Map<String, String> socialMediaUrls = new HashMap<>();

    private Set<Genre>

    //TODO Followers


}
