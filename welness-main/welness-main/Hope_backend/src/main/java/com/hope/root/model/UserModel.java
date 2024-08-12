package com.hope.root.model;

import java.util.List;

public class UserModel {

	private int userId;
	
	private String emailId;
		
	private String dob;
	
	private String password;
	
	private String mobileNo;
	
	private String gender;

	private String role;
	
	private String name;
	
	private String doctorRegId;
	
	List<DoctorHospital> doctorHospitalList;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDoctorRegId() {
		return doctorRegId;
	}

	public void setDoctorRegId(String doctorRegId) {
		this.doctorRegId = doctorRegId;
	}

	public List<DoctorHospital> getDoctorHospitalList() {
		return doctorHospitalList;
	}

	public void setDoctorHospitalList(List<DoctorHospital> doctorHospitalList) {
		this.doctorHospitalList = doctorHospitalList;
	}
	
	
	
	
	
	

}
