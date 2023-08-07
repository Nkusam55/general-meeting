package com.liafi.gcmeeting.service;

import java.util.List;

import javax.mail.MessagingException;

import com.liafi.gcmeeting.dto.UserDto;
import com.liafi.gcmeeting.entity.ClubTypeList;
import com.liafi.gcmeeting.entity.User;
import com.liafi.gcmeeting.entity.ZoneList;

/**
 * @author narendra kusam
 */

public interface UserService {
   
	void saveUser(UserDto userDto) throws Exception;

    User findByEmail(boolean status,String mailId);
    
    User findById(long id);

    List<UserDto> findAllUsers(boolean status);

	List<ZoneList> getZoneList();
	
	ZoneList getZoneName(long zoneId);
    
	User updateUserData(User user);

	String sendMailAfterPayment(User user) throws MessagingException;

	ClubTypeList getClubTypeName(long clubTypeId);
	
	List<ClubTypeList> getClubTypeList();

	UserDto convertEntityToDto(User user);

	List<UserDto> findAll();

	boolean checkCred(String mailId, String password);

}
