package com.generalbody.service;

import java.util.List;

import com.generalbody.dto.UserDto;
import com.generalbody.entity.User;

/**
 * @author narendra kusam
 */

public interface UserService {
    void saveUser(UserDto userDto) throws Exception;

    User findByEmail(String mailId);

    List<UserDto> findAllUsers();
}
