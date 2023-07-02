package com.generalbody.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.generalbody.dto.UserDto;
import com.generalbody.entity.Role;
import com.generalbody.entity.User;
import com.generalbody.repository.RoleRepository;
import com.generalbody.repository.UserRepository;
import com.generalbody.service.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author narendra kusam
 */


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) throws Exception {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setZoneId(userDto.getZoneDetails().getId());
        user.setDivisionName(userDto.getDivisionName());
        user.setBranch(userDto.getBranch());
        user.setAgencyName(userDto.getAgencyName());
        user.setPatternMembership(userDto.isMembershipPattern());
        user.setMembershipType(userDto.getMembershipType());
        user.setMembershipNumber(userDto.getMembershipNumber());
        user.setAadharNumber(userDto.getAadharNumber());
      
        try {
			user.setAadharDocument(userDto.getAadharFile().getBytes());
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
        	user.setRoles(Arrays.asList(adminRole));
        }
        
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
