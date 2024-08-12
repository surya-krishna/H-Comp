package com.hope.root.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user_tests database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="user_tests")
@NamedQuery(name="UserTest.findAll", query="SELECT u FROM UserTest u")
public class UserTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_test_id")
	private int userTestId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="test_max_normal")
	private BigDecimal testMaxNormal;

	@Column(name="test_min_normal")
	private BigDecimal testMinNormal;

	@Column(name="test_name")
	private String testName;


	@Column(name="units")
	private String units;

	@Column(name="test_observation")
	private String testResultText;

	@Column(name="test_result_type")
	private String testResultType;
	
	@Column(name="observation")
	private String observation;
	
	@Column(name="suggestions")
	private String suggestions;
	
	@Column(name="test_value")
	private String testValue;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to Report
	@ManyToOne
	@JoinColumn(name="report_id")
	private Report report;

	//bi-directional many-to-one association to TestMaster
	@ManyToOne
	@JoinColumn(name="test_master_id")
	private TestMaster testMaster;

	//bi-directional many-to-one association to TestFile
	@OneToMany(mappedBy="userTest",cascade = CascadeType.ALL)
	private List<TestFile> testFiles;

	//bi-directional many-to-one association to Observation
	@OneToMany(mappedBy="userTest",cascade = CascadeType.ALL)
	private List<Medication> medication;

	public UserTest() {
	}

	public int getUserTestId() {
		return this.userTestId;
	}

	public void setUserTestId(int userTestId) {
		this.userTestId = userTestId;
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

	public BigDecimal getTestMaxNormal() {
		return this.testMaxNormal;
	}

	public void setTestMaxNormal(BigDecimal testMaxNormal) {
		this.testMaxNormal = testMaxNormal;
	}

	public BigDecimal getTestMinNormal() {
		return this.testMinNormal;
	}

	public void setTestMinNormal(BigDecimal testMinNormal) {
		this.testMinNormal = testMinNormal;
	}

	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestResultType() {
		return this.testResultType;
	}

	public void setTestResultType(String testResultType) {
		this.testResultType = testResultType;
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

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public TestMaster getTestMaster() {
		return this.testMaster;
	}

	public void setTestMaster(TestMaster testMaster) {
		this.testMaster = testMaster;
	}

	public List<TestFile> getTestFiles() {
		return this.testFiles;
	}

	public void setTestFiles(List<TestFile> testFiles) {
		this.testFiles = testFiles;
	}

	public TestFile addTestFile(TestFile testFile) {
		getTestFiles().add(testFile);
		testFile.setUserTest(this);

		return testFile;
	}

	public TestFile removeTestFile(TestFile testFile) {
		getTestFiles().remove(testFile);
		testFile.setUserTest(null);

		return testFile;
	}
	


	public List<Medication> getmedications() {
		return this.medication;
	}

	public void setmedications(List<Medication> medications) {
		this.medication = medications;
	}

	public Medication addmedication(Medication medication) {
		getmedications().add(medication);
		medication.setUserTest(this);

		return medication;
	}

	public Medication removemedication(Medication medication) {
		getmedications().remove(medication);
		medication.setUserTest(null);

		return medication;
	}

	
	

}