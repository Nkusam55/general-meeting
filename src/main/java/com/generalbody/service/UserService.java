package com.generalbody.service;

import java.util.List;

import com.generalbody.dto.UserDto;
import com.generalbody.entity.User;
import com.generalbody.entity.ZoneList;

/**
 * @author narendra kusam
 */

public interface UserService {
    void saveUser(UserDto userDto) throws Exception;

    User findByEmail(boolean status,String mailId);

    List<UserDto> findAllUsers();

	List<ZoneList> getZoneList();
	
	ZoneList getZoneName(long zoneId);
}
