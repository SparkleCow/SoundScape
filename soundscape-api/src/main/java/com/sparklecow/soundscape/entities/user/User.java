package com.sparklecow.soundscape.entities.user;

import com.sparklecow.soundscape.models.user.Role;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//This allows to have creation and update information for this class
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true, insertable = false)
    private LocalDateTime lastModifiedAt;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String profileImageUrl;

    private String bannerImageUrl;

    private LocalDate birthDate;

    private String country;

    private Boolean isVerified;

    private Boolean isEnabled = false;

    private Boolean isAccountExpired = false;

    private Boolean isAccountLocked = false;

    private Boolean isCredentialsExpired = false;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.name())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
