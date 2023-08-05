package com.liafi.gcmeeting.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.liafi.gcmeeting.config.EmailService;
import com.liafi.gcmeeting.dto.UserDto;
import com.liafi.gcmeeting.entity.ClubTypeList;
import com.liafi.gcmeeting.entity.Relative;
import com.liafi.gcmeeting.entity.Role;
import com.liafi.gcmeeting.entity.User;
import com.liafi.gcmeeting.entity.ZoneList;
import com.liafi.gcmeeting.repository.ClubListRepository;
import com.liafi.gcmeeting.repository.RelativesRepository;
import com.liafi.gcmeeting.repository.RoleRepository;
import com.liafi.gcmeeting.repository.UserRepository;
import com.liafi.gcmeeting.repository.ZoneListRepository;
import com.liafi.gcmeeting.service.UserService;


/**
 * @author narendra kusam
 */


@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ZoneListRepository zoneListRepository;
	private PasswordEncoder passwordEncoder;
	private ClubListRepository clubListRepository;
	private RelativesRepository relativesRepository;
	
	@Autowired
	EmailService mailService;

	public UserServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			ZoneListRepository zoneListRepository,
			ClubListRepository clubListRepository,
			PasswordEncoder passwordEncoder,
			RelativesRepository relativesRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.zoneListRepository = zoneListRepository;
		this.clubListRepository = clubListRepository;
		this.passwordEncoder = passwordEncoder;
		this.relativesRepository = relativesRepository;
	}

	@Override
	@Transactional
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
		user.setClubTypeId(Long.valueOf(userDto.getClubTypeId()));
		user.setAcceptTerms(userDto.isAcceptTerms());
		String appId = generateUniqueString(userDto.getFirstName());
		user.setAppointmentId(appId);
		user.setStatus(false);
		
		try {
			user.setPhoto(userDto.getPhoto().getBytes());
		} catch (IOException e) {
			throw new Exception("Photo uploading failed");
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
			User savedUser = userRepository.save(user);
			List<Relative> relatives = new ArrayList<>();
	        for (Relative relative : userDto.getRelatives()) {
	            relative.setUser(savedUser);
	            relativesRepository.save(relative);
	            relatives.add(relative);
	        }

	       // user.setRelatives(relatives);

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
	    if(user.getClubTypeId()!=null) {
	     ClubTypeList clubTypeList = clubListRepository.findById(user.getClubTypeId()).get();	    	
		 userDto.setClubTypeName(clubTypeList.getName());
	    }
	    
	    List<Relative> relativesList = relativesRepository.findByUserId(user.getId());
	    userDto.setRelatives(relativesList);
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
	
	@Override
	public List<ClubTypeList> getClubTypeList() {
		List<ClubTypeList> clubTypeList = clubListRepository.findAll();
		return clubTypeList;
	}

	@Override
	public ClubTypeList getClubTypeName(long clubTypeId) {
		return clubListRepository.findById(clubTypeId).get();
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
