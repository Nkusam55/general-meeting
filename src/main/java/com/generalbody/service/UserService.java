package com.generalbody.service;

import java.util.List;

import javax.mail.MessagingException;

import com.generalbody.dto.UserDto;
import com.generalbody.entity.ClubTypeList;
import com.generalbody.entity.User;
import com.generalbody.entity.ZoneList;

/**
 * @author narendra kusam
 */

public interface UserService {
   
	void saveUser(UserDto userDto) throws Exception;

    User findByEmail(boolean status,String mailId);

    List<UserDto> findAllUsers(boolean status);

	List<ZoneList> getZoneList();
	
	ZoneList getZoneName(long zoneId);
    
	int activateUserStatus(boolean status, long userId);

	String sendMailAfterPayment(User user) throws MessagingException;

	ClubTypeList getClubTypeName(long clubTypeId);
	
	List<ClubTypeList> getClubTypeList();

}
