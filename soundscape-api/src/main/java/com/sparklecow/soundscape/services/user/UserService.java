package com.sparklecow.soundscape.services.user;

import com.sparklecow.soundscape.models.user.*;
import com.sparklecow.soundscape.services.common.CrudService;

public interface UserService extends CrudService<UserRequestDto, UserResponseDto, UserUpdateDto> {

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto);
}
