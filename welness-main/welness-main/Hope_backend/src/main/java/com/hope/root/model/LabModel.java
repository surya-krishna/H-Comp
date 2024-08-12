package com.hope.root.model;

import java.util.List;

public class LabModel {
	
	private String labNumber;
	
	private String address;
	
	private String labName;
	
	private String labRegId;
	
	private String labGstNo;

	private int currentReceiptNumber;
	
	private String timings;
	
	private UserModel labAdmin;

	public String getLabNumber() {
		return labNumber;
	}

	public void setLabNumber(String labNumber) {
		this.labNumber = labNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabRegId() {
		return labRegId;
	}

	public void setLabRegId(String labRegId) {
		this.labRegId = labRegId;
	}

	public String getLabGstNo() {
		return labGstNo;
	}

	public void setLabGstNo(String labGstNo) {
		this.labGstNo = labGstNo;
	}

	public int getCurrentReceiptNumber() {
		return currentReceiptNumber;
	}

	public void setCurrentReceiptNumber(int currentReceiptNumber) {
		this.currentReceiptNumber = currentReceiptNumber;
	}

	public String getTimings() {
		return timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
	}

	public UserModel getLabAdmin() {
		return labAdmin;
	}

	public void setLabAdmin(UserModel labAdmin) {
		this.labAdmin = labAdmin;
	}
	
}
