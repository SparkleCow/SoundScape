package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.entities.user.User;
import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.services.common.CrudService;
import jakarta.mail.MessagingException;

public interface UserService extends CrudService<UserRequestDto, UserResponseDto, UserUpdateDto> {

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto);

    void sendValidation(User user) throws MessagingException;

    String saveAndGenerateToken(User user);

    String generateToken(Integer tokenLength);

    void validateToken(String token) throws MessagingException;
}
