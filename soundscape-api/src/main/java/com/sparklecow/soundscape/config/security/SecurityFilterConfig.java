package com.sparklecow.soundscape.config.security;

import com.sparklecow.soundscape.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {

    private static final String ADMIN_ROLE = "ADMIN";

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/admin/register").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/user/delete/me").authenticated()
                        .requestMatchers("/user/delete/**").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/user/me").authenticated()
                        .requestMatchers("/user").hasAuthority(ADMIN_ROLE)

                        .requestMatchers("/artist/admin").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/artist/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/artist").authenticated()
                        .requestMatchers(HttpMethod.GET, "/artist").permitAll()
                        .requestMatchers(HttpMethod.GET, "/artist/*").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/artist/me/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/artist/*").hasAuthority(ADMIN_ROLE)

                        .requestMatchers(HttpMethod.GET,"/album").permitAll()
                        .requestMatchers(HttpMethod.GET,"/album/artist").permitAll()
                        .requestMatchers(HttpMethod.GET, "/album/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/album").authenticated()
                        .requestMatchers(HttpMethod.POST, "/album/admin").hasAuthority(ADMIN_ROLE)

                        .requestMatchers(HttpMethod.GET,"/song").permitAll()
                        .requestMatchers(HttpMethod.GET,"/song/artist").permitAll()
                        .requestMatchers(HttpMethod.GET,"/song/album").permitAll()
                        .requestMatchers(HttpMethod.POST, "/song").authenticated()
                        .requestMatchers(HttpMethod.POST, "/song/admin").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/song/admin/bulk").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE,"/song/**").hasAuthority(ADMIN_ROLE)

                        .requestMatchers(HttpMethod.GET, "/playlist/admin/*").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/playlist/admin/*").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/playlist/admin/*").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, "/playlist/admin/search").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, "/playlist/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/playlist/me").authenticated()

                        .requestMatchers(HttpMethod.GET, "/playlist").permitAll()
                        .requestMatchers(HttpMethod.POST, "/playlist").authenticated()
                        .requestMatchers(HttpMethod.GET, "/playlist/*").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/playlist/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/playlist/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/playlist/*").authenticated()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
