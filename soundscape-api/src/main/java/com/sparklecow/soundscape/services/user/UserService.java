package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.services.common.CrudService;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService extends CrudService<UserRequestDto, UserResponseDto, UserUpdateDto> {

    UserResponseDto getUserInformation(Authentication authentication);

    UserResponseDto createAdmin(UserRequestDto userRequestDto);

    Page<UserResponseDto> findByUsername(String username, Pageable pageable);

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto);

    void sendValidation(User user) throws MessagingException;

    String saveAndGenerateToken(User user);

    String generateToken(Integer tokenLength);

    void validateToken(String token) throws MessagingException;
}
