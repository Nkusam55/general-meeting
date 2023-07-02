package com.generalbody.entity;

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

/**
 * @author narendra kusam
 */

@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "division_name", nullable = false)
	private String divisionName;
	
	@Column(name = "branch_name", nullable = false)
	private String branch;
	
	@Column(name = "agency_name", nullable = false)
	private String agencyName;
	
	@Column(name = "pattern_membership", nullable = false)
	private boolean patternMembership;
	
	@Column(name = "membership_type", nullable = false)
	private String membershipType;
	
	@Column(name = "membership_number", nullable = false)
	private String membershipNumber;
	
	@Column(name = "aadhar_number", nullable = false)
	private String aadharNumber;
	
	@Lob
    @Column(name = "aadhar_doc")
	private byte[] aadharDocument;
	
    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public boolean isPatternMembership() {
		return patternMembership;
	}

	public void setPatternMembership(boolean patternMembership) {
		this.patternMembership = patternMembership;
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

	public byte[] getAadharDocument() {
		return aadharDocument;
	}

	public void setAadharDocument(byte[] aadharDocument) {
		this.aadharDocument = aadharDocument;
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
    
}
