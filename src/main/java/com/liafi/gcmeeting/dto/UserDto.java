package com.liafi.gcmeeting.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.liafi.gcmeeting.entity.Relative;

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
    
    private String appointmentId;
  	
	private int zoneId;
    
  	private String divisionName;
  	
  	private String branch;
  	
  	private String agencyCode;
  	
  	private boolean membershipPattern;
  	
  	private String membershipType;
  	
  	private String membershipNumber;
  	
  	private String aadharNumber;
  	
  	private MultipartFile photo;
  	
  	private String name;

  	private String zoneName;
  	
    private boolean status;
    
    private String amount;
    
    private int clubTypeId;
    
    private String clubTypeName;
    
    private boolean acceptTerms;
    
    private List<Relative> relatives;
    
	private String paymentId;

	private String orderId;
	
	private String tempPaymentId;

	private String tempOrderId;
  	
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

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
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

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getClubTypeId() {
		return clubTypeId;
	}

	public void setClubTypeId(int clubTypeId) {
		this.clubTypeId = clubTypeId;
	}

	public String getClubTypeName() {
		return clubTypeName;
	}

	public void setClubTypeName(String clubTypeName) {
		this.clubTypeName = clubTypeName;
	}

	public boolean isAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public List<Relative> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTempPaymentId() {
		return tempPaymentId;
	}

	public void setTempPaymentId(String tempPaymentId) {
		this.tempPaymentId = tempPaymentId;
	}

	public String getTempOrderId() {
		return tempOrderId;
	}

	public void setTempOrderId(String tempOrderId) {
		this.tempOrderId = tempOrderId;
	}
	
	
	
}