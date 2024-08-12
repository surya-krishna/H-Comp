package com.hope.root.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user_otp database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="user_otp")
@NamedQuery(name="UserOtp.findAll", query="SELECT u FROM UserOtp u")
public class UserOtp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="otp_id")
	private int otpId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="expiry_time")
	private Timestamp expiryTime;

	@Column(name="otp_number")
	private int otpNumber;

	@Column(name="otp_type")
	private String otpType;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="attempts")
	private int attempts;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	public UserOtp() {
	}

	public int getOtpId() {
		return this.otpId;
	}

	public void setOtpId(int otpId) {
		this.otpId = otpId;
	}

	public int getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public Timestamp getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}

	public int getOtpNumber() {
		return this.otpNumber;
	}

	public void setOtpNumber(int otpNumber) {
		this.otpNumber = otpNumber;
	}

	public String getOtpType() {
		return this.otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	

}