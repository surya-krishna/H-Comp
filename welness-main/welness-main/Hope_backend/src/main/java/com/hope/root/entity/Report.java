package com.hope.root.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hope.root.config.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the reports database table.
 * 
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="reports")
@NamedQuery(name="Report.findAll", query="SELECT r FROM Report r")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="report_id")
	private int reportId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="lab_address")
	private String labAddress;

	@Column(name="lab_name")
	private String labName;


	@Column(name="lab_phone")
	private String labPhone;

	@Column(name="gender")
	private String gender;


	@Column(name="age")
	private int age;
	
	@Column(name="name_on_report")
	private String nameOnReport;

	@Temporal(TemporalType.DATE)
	@Column(name="report_date")
	private Date reportDate;

	@Column(name="report_status")
	private String reportStatus;
	

	@Column(name="report_pdf_id")
	private String reportPdfId;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	@Column(name = "contains_pii")
	private Boolean containsPII;

	@Column(name = "report_download_url")
	private String reportDownloadUrl;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to LabDetail
	@ManyToOne
	@JoinColumn(name="lab_id")
	private LabDetail labDetail;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="operator_id")
	private User operator;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="incharge_id")
	private User incharge;

	//bi-directional many-to-one association to UserTest
	@OneToMany(mappedBy="report",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	//@Singular("userTests")
	private List<UserTest> userTests;

	//bi-directional many-to-one association to medication
	@OneToMany(mappedBy="report")
	//@Singular("medication")
	private List<Medication> medication;


	
	public int getReportId() {
		return reportId;
	}


	public void setReportId(int reportId) {
		this.reportId = reportId;
	}


	public int getActiveStatus() {
		return activeStatus;
	}


	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Timestamp getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}


	public String getLabAddress() {
		return Utility.decrypt(labAddress);
	}


	public void setLabAddress(String labAddress) {
		this.labAddress = Utility.encrypt(labAddress);
	}


	public String getLabName() {
		return Utility.decrypt(labName);
	}


	public void setLabName(String labName) {
		this.labName = Utility.encrypt(labName);
	}


	public String getNameOnReport() {
		return Utility.decrypt(this.nameOnReport);
	}


	public void setNameOnReport(String nameOnReport) {
		
		this.nameOnReport = Utility.encrypt(nameOnReport);
	}


	public Date getReportDate() {
		return reportDate;
	}


	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}


	public String getReportStatus() {
		return reportStatus;
	}


	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Timestamp getUpdatedTime() {
		return updatedTime;
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


	public LabDetail getLabDetail() {
		return labDetail;
	}


	public void setLabDetail(LabDetail labDetail) {
		this.labDetail = labDetail;
	}


	public User getOperator() {
		return operator;
	}


	public void setOperator(User operator) {
		this.operator = operator;
	}


	public User getIncharge() {
		return incharge;
	}


	public void setIncharge(User incharge) {
		this.incharge = incharge;
	}


	public List<UserTest> getUserTests() {
		return this.userTests;
	}

	public void setUserTests(List<UserTest> userTests) {
		this.userTests = userTests;
	}

	public UserTest addUserTest(UserTest userTest) {
		getUserTests().add(userTest);
		userTest.setReport(this);

		return userTest;
	}

	public UserTest removeUserTest(UserTest userTest) {
		getUserTests().remove(userTest);
		userTest.setReport(null);

		return userTest;
	}

	public List<Medication> getmedications() {
		return this.medication;
	}

	public void setmedications(List<Medication> medications) {
		this.medication = medications;
	}

	public Medication addmedication(Medication medication) {
		getmedications().add(medication);
		medication.setReport(this);

		return medication;
	}

	public Medication removemedication(Medication medication) {
		getmedications().remove(medication);
		medication.setReport(null);

		return medication;
	}


	public String getLabPhone() {
		return Utility.decrypt(labPhone);
	}


	public void setLabPhone(String labPhone) {
		this.labPhone = Utility.encrypt(labPhone);
	}


	public String getGender() {
		return Utility.decrypt(gender);
	}


	public void setGender(String gender) {
		this.gender = Utility.encrypt(gender);
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getReportPdfId() {
		return Utility.decrypt(reportPdfId);
	}


	public void setReportPdfId(String reportPdfId) {
		this.reportPdfId = Utility.encrypt(reportPdfId);
	}


	public Boolean getContainsPII() {
		return containsPII;
	}


	public void setContainsPII(Boolean containsPII) {
		this.containsPII = containsPII;
	}


	public String getReportDownloadUrl() {
		return Utility.decrypt(reportDownloadUrl);
	}


	public void setReportDownloadUrl(String reportDownloadUrl) {
		this.reportDownloadUrl = Utility.encrypt(reportDownloadUrl);
	}


	public List<Medication> getMedication() {
		return medication;
	}


	public void setMedication(List<Medication> medication) {
		this.medication = medication;
	}

}