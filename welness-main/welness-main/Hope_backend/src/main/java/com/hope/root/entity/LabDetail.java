package com.hope.root.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the lab_details database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="lab_details")
@NamedQuery(name="LabDetail.findAll", query="SELECT l FROM LabDetail l")
public class LabDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lab_id")
	private int labId;

	@Column(name="active_status")
	private int activeStatus;

	private String address;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="current_receipt_number")
	private int currentReceiptNumber;

	@Column(name="lab_gst_no")
	private String labGstNo;

	@Column(name="lab_name")
	private String labName;

	@Column(name="lab_number")
	private String labNumber;

	@Column(name="lab_reg_id")
	private String labRegId;

	private String timings;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="labDetail",cascade = CascadeType.ALL)
	private List<User> users;

	//bi-directional many-to-one association to Report
	@OneToMany(mappedBy="labDetail")
	private List<Report> reports;

	//bi-directional many-to-one association to LabTest
	@OneToMany(mappedBy="labDetail")
	private List<LabTest> labTests;

	public LabDetail() {
	}

	public int getLabId() {
		return this.labId;
	}

	public void setLabId(int labId) {
		this.labId = labId;
	}

	public int getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getCurrentReceiptNumber() {
		return this.currentReceiptNumber;
	}

	public void setCurrentReceiptNumber(int currentReceiptNumber) {
		this.currentReceiptNumber = currentReceiptNumber;
	}

	public String getLabGstNo() {
		return this.labGstNo;
	}

	public void setLabGstNo(String labGstNo) {
		this.labGstNo = labGstNo;
	}

	public String getLabName() {
		return this.labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabNumber() {
		return this.labNumber;
	}

	public void setLabNumber(String labNumber) {
		this.labNumber = labNumber;
	}

	public String getLabRegId() {
		return this.labRegId;
	}

	public void setLabRegId(String labRegId) {
		this.labRegId = labRegId;
	}

	public String getTimings() {
		return this.timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
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

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setLabDetail(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setLabDetail(null);

		return user;
	}

	public List<Report> getReports() {
		return this.reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Report addReport(Report report) {
		getReports().add(report);
		report.setLabDetail(this);

		return report;
	}

	public Report removeReport(Report report) {
		getReports().remove(report);
		report.setLabDetail(null);

		return report;
	}

	public List<LabTest> getLabTests() {
		return this.labTests;
	}

	public void setLabTests(List<LabTest> labTests) {
		this.labTests = labTests;
	}

	public LabTest addLabTest(LabTest labTest) {
		getLabTests().add(labTest);
		labTest.setLabDetail(this);

		return labTest;
	}

	public LabTest removeLabTest(LabTest labTest) {
		getLabTests().remove(labTest);
		labTest.setLabDetail(null);

		return labTest;
	}

}