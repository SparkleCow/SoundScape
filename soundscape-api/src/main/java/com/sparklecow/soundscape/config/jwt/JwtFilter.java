package com.sparklecow.soundscape.config.jwt;

import com.sparklecow.soundscape.exceptions.ExpiredTokenException;
import com.sparklecow.soundscape.exceptions.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = token.substring(7);
        String username = null;

        try {
            username = jwtUtils.extractUsername(jwt);
        } catch (InvalidTokenException e) {
            handleException(response, "Token is not valid", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (ExpiredTokenException e) {
            handleException(response, "Token has expired", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtUtils.validateToken(jwt, user);
        } catch (ExpiredTokenException e) {
            handleException(response, "Token has expired", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (InvalidTokenException e) {
            handleException(response, "Token is not valid", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request,response);
    }

    /**
     * This method allows to continue with the filter if the endpoint is public, otherwise it will validate whether the token is valid or not.
     * This is useful because for public endpoints it is irrelevant to know whether token exist, is empty or is valid */
    private boolean isPublicEndpoint(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        return (requestURI.equals("/artist") && httpMethod.equals("GET")) ||

                requestURI.startsWith("/auth/validate-token") ||
                requestURI.startsWith("/auth/login") ||
                requestURI.startsWith("/auth/register") ||

                (requestURI.startsWith("/album") && httpMethod.equals("GET"));
    }

    /**
     * Handles exceptions by returning a specific error message and status code in the HTTP response.
     * This method is used to standardize error responses in the JWT filter.
     *
     * @param response    The HttpServletResponse object used to send the error response.
     * @param message     The error message to be included in the response body.
     * @param statusCode  The HTTP status code to be set in the response (e.g., 401 for unauthorized).
     */
    private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        /*The use of \" is to escape double quotes within the string,
        since quotes are necessary to correctly represent the JSON format.*/
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
