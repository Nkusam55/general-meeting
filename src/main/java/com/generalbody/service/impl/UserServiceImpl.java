package com.generalbody.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.generalbody.config.EmailService;
import com.generalbody.dto.UserDto;
import com.generalbody.entity.Role;
import com.generalbody.entity.User;
import com.generalbody.entity.ZoneList;
import com.generalbody.repository.RoleRepository;
import com.generalbody.repository.UserRepository;
import com.generalbody.repository.ZoneListRepository;
import com.generalbody.service.UserService;


/**
 * @author narendra kusam
 */


@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ZoneListRepository zoneListRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	EmailService mailService;

	public UserServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			ZoneListRepository zoneListRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.zoneListRepository = zoneListRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto userDto) throws Exception {
		User user = new User();
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setAddress(userDto.getAddress());
		user.setEmail(userDto.getEmail());
		user.setMobile(userDto.getMobile());
		user.setZoneId(Long.valueOf(userDto.getZoneId()));
		user.setDivisionName(userDto.getDivisionName());
		user.setBranch(userDto.getBranch());
		user.setAgencyCode(userDto.getAgencyCode());
		user.setMembershipPattern(userDto.isMembershipPattern());
		user.setMembershipType(userDto.getMembershipType());
		user.setMembershipNumber(userDto.getMembershipNumber());
		user.setAadharNumber(userDto.getAadharNumber());
		String appId = generateUniqueString(userDto.getFirstName());
		user.setAppointmentId(appId);
		user.setStatus(false);
		
		try {
			user.setPhoto(userDto.getPhoto().getBytes());
		} catch (IOException e) {
			throw new Exception("Aadhar uploading failed");
		}

		//encrypt the password once we integrate spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		boolean isUserFlag = true;

		if(adminRole == null){
			adminRole = checkRoleExist("ROLE_ADMIN");
			isUserFlag = false;
		}

		if(isUserFlag) {
			Role userRole = roleRepository.findByName("ROLE_USER");
			if(userRole == null){
				userRole = checkRoleExist("ROLE_USER");
			}
			user.setRoles(Arrays.asList(userRole));
		}else {
			user.setStatus(true);
			user.setRoles(Arrays.asList(adminRole));
		}
		try {
			userRepository.save(user);
		}catch (Exception e) {
			throw new Exception(e);
		}

	}

	@Override
	public User findByEmail(boolean status,String email) {
		return userRepository.findByEmail(status,email);
	}

	@Override
	public List<UserDto> findAllUsers(boolean status) {
		List<User> users = userRepository.findUserByStatus(status);
		return users.stream().map((user) -> convertEntityToDto(user))
				.collect(Collectors.toList());
	}

	private UserDto convertEntityToDto(User user){
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
        userDto.setAadharNumber(user.getAadharNumber());
        userDto.setDivisionName(user.getDivisionName());
        userDto.setBranch(user.getBranch());
        userDto.setMembershipNumber(user.getMembershipNumber());
        userDto.setAgencyCode(user.getAgencyCode());
        userDto.setAppointmentId(user.getAppointmentId());
		ZoneList zone = zoneListRepository.findById(user.getZoneId()).get();
	    userDto.setZoneName(zone.getName());	
		return userDto;
	}

	private Role checkRoleExist(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return roleRepository.save(role);
	}

	@Override
	public List<ZoneList> getZoneList() {
		List<ZoneList> zoneList = zoneListRepository.findAll();
		return zoneList;
	}

	@Override
	public ZoneList getZoneName(long zoneId) {
		return zoneListRepository.findById(zoneId).get();
	}
	
	public static String generateUniqueString(String name) {
		Random random = new Random();
		int randomNumber = random.nextInt(1000000);
		String uniqueString = name + "_" + randomNumber;
		return uniqueString;
	}

	@Override
	public int activateUserStatus(boolean status, long userId) {
		return userRepository.activateUserStatus(status, userId);
	}
	
	@Override
	public String sendMailAfterPayment(User user) throws MessagingException {
		return mailService.sendMail(user);
	}
	
}
