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
 * The persistent class for the sharing_details database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="sharing_details")
@NamedQuery(name="SharingDetail.findAll", query="SELECT s FROM SharingDetail s")
public class SharingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sharing_id")
	private int sharingId;

	@Column(name="accessed_on")
	private Timestamp accessedOn;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="expiry_time")
	private int expiryTime;

	@Column(name="otp_number")
	private int otpNumber;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional one-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private User doctor;

	public SharingDetail() {
	}

	public int getSharingId() {
		return this.sharingId;
	}

	public void setSharingId(int sharingId) {
		this.sharingId = sharingId;
	}

	public Timestamp getAccessedOn() {
		return this.accessedOn;
	}

	public void setAccessedOn(Timestamp accessedOn) {
		this.accessedOn = accessedOn;
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

	public int getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(int expiryTime) {
		this.expiryTime = expiryTime;
	}

	public int getOtpNumber() {
		return this.otpNumber;
	}

	public void setOtpNumber(int otpNumber) {
		this.otpNumber = otpNumber;
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
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}


}