package com.generalbody.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.generalbody.entity.ZoneList;

/**
 * @author narendra kusam
 */

public class UserDto
{
    private Long id;
    
    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String lastName;
    
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    
    @NotEmpty(message = "Password should not be empty")
    private String password;
	
    @NotEmpty(message = "Mail Id should not be empty")
    private String address;
  	
    @NotEmpty(message = "Mobile Number should not be empty")
  	private String mobile;
  	
	private ZoneList zoneDetails;
    
  	private String divisionName;
  	
  	private String branch;
  	
  	private String agencyName;
  	
  	private boolean membershipPattern;
  	
  	private String membershipType;
  	
  	private String membershipNumber;
  	
  	private String aadharNumber;
  	
  	private MultipartFile aadharFile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public boolean isMembershipPattern() {
		return membershipPattern;
	}

	public void setMembershipPattern(boolean membershipPattern) {
		this.membershipPattern = membershipPattern;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public MultipartFile getAadharFile() {
		return aadharFile;
	}

	public void setAadharFile(MultipartFile aadharFile) {
		this.aadharFile = aadharFile;
	}

	public ZoneList getZoneDetails() {
		return zoneDetails;
	}

	public void setZoneDetails(ZoneList zoneDetails) {
		this.zoneDetails = zoneDetails;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}
	
}
