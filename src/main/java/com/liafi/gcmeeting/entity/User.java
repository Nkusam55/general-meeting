package com.liafi.gcmeeting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author narendra kusam
 */

@Entity
@Table(name="users")
public class User
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String address;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String mobile;

	@Column(name = "zone_id", nullable = false)
	private Long zoneId;

	@Column(name = "club_type_id", nullable = false)
	private Long clubTypeId;

	@Column(name = "division_name", nullable = false)
	private String divisionName;

	@Column(name = "branch_name", nullable = false)
	private String branch;

	@Column(name = "agency_code", nullable = false)
	private String agencyCode;

	@Column(name = "membership_pattern", nullable = false)
	private boolean membershipPattern;

	@Column(name = "membership_type", nullable = false)
	private String membershipType;

	@Column(name = "membership_number", nullable = false)
	private String membershipNumber;

	@Column(name = "aadhar_number", nullable = false)
	private String aadharNumber;

	@Lob
	@Column(name = "photo")
	private byte[] photo;

	@Column(nullable=false)
	private String password;

	@Column(name = "appointment_id", nullable = false)
	private String appointmentId;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="users_roles",
			joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
	private List<Role> roles = new ArrayList<>();

    @Transient
	private List<Relative> relatives = new ArrayList<>();

	@Column(name = "status", nullable = false)
	private boolean status;

	@Column(name = "accept_terms", nullable = false)
	private boolean acceptTerms;

	@Transient
	private String imageData;
	
	@Column(name = "payment_id", nullable = false)
	private String paymentId;

	@Column(name = "order_id", nullable = false)
	private String orderId;
	
	@Column(name = "temp_payment_id", nullable = false)
	private String tempPaymentId;

	@Column(name = "temp_order_id", nullable = false)
	private String tempOrderId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
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

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getClubTypeId() {
		return clubTypeId;
	}

	public void setClubTypeId(Long clubTypeId) {
		this.clubTypeId = clubTypeId;
	}

	public boolean isAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
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
	
	/*
	 * public void addRelative(Relative relative) { relatives.add(relative);
	 * relative.setUser(this); }
	 * 
	 * public void removeRelative(Relative relative) { relatives.remove(relative);
	 * relative.setUser(null); }
	 */
	
	

}
