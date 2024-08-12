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
 * The persistent class for the test_master database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="test_master")
@NamedQuery(name="TestMaster.findAll", query="SELECT t FROM TestMaster t")
public class TestMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="test_master_id")
	private int testMasterId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="test_code")
	private String testCode;

	@Column(name="test_description")
	private String testDescription;

	@Column(name="test_name")
	private String testName;

	@Column(name="test_result_type")
	private String testResultType;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to TestMasterName
	@OneToMany(mappedBy="testMaster")
	private List<TestMasterName> testMasterNames;

	//bi-directional many-to-one association to TestUnitsMaster
	@OneToMany(mappedBy="testMaster")
	private List<TestUnitsMaster> testUnitsMasters;

	//bi-directional many-to-one association to LabTest
	@OneToMany(mappedBy="testMaster")
	private List<LabTest> labTests;

	//bi-directional many-to-one association to LabNormalRange
	@OneToMany(mappedBy="testMaster")
	private List<LabNormalRange> labNormalRanges;

	//bi-directional many-to-one association to UserTest
	@OneToMany(mappedBy="testMaster")
	private List<UserTest> userTests;

	public TestMaster() {
	}

	public int getTestMasterId() {
		return this.testMasterId;
	}

	public void setTestMasterId(int testMasterId) {
		this.testMasterId = testMasterId;
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

	public String getTestCode() {
		return this.testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getTestDescription() {
		return this.testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
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

	public List<TestMasterName> getTestMasterNames() {
		return this.testMasterNames;
	}

	public void setTestMasterNames(List<TestMasterName> testMasterNames) {
		this.testMasterNames = testMasterNames;
	}

	public TestMasterName addTestMasterName(TestMasterName testMasterName) {
		getTestMasterNames().add(testMasterName);
		testMasterName.setTestMaster(this);

		return testMasterName;
	}

	public TestMasterName removeTestMasterName(TestMasterName testMasterName) {
		getTestMasterNames().remove(testMasterName);
		testMasterName.setTestMaster(null);

		return testMasterName;
	}

	public List<TestUnitsMaster> getTestUnitsMasters() {
		return this.testUnitsMasters;
	}

	public void setTestUnitsMasters(List<TestUnitsMaster> testUnitsMasters) {
		this.testUnitsMasters = testUnitsMasters;
	}

	public TestUnitsMaster addTestUnitsMaster(TestUnitsMaster testUnitsMaster) {
		getTestUnitsMasters().add(testUnitsMaster);
		testUnitsMaster.setTestMaster(this);

		return testUnitsMaster;
	}

	public TestUnitsMaster removeTestUnitsMaster(TestUnitsMaster testUnitsMaster) {
		getTestUnitsMasters().remove(testUnitsMaster);
		testUnitsMaster.setTestMaster(null);

		return testUnitsMaster;
	}

	public List<LabTest> getLabTests() {
		return this.labTests;
	}

	public void setLabTests(List<LabTest> labTests) {
		this.labTests = labTests;
	}

	public LabTest addLabTest(LabTest labTest) {
		getLabTests().add(labTest);
		labTest.setTestMaster(this);

		return labTest;
	}

	public LabTest removeLabTest(LabTest labTest) {
		getLabTests().remove(labTest);
		labTest.setTestMaster(null);

		return labTest;
	}

	public List<LabNormalRange> getLabNormalRanges() {
		return this.labNormalRanges;
	}

	public void setLabNormalRanges(List<LabNormalRange> labNormalRanges) {
		this.labNormalRanges = labNormalRanges;
	}

	public LabNormalRange addLabNormalRange(LabNormalRange labNormalRange) {
		getLabNormalRanges().add(labNormalRange);
		labNormalRange.setTestMaster(this);

		return labNormalRange;
	}

	public LabNormalRange removeLabNormalRange(LabNormalRange labNormalRange) {
		getLabNormalRanges().remove(labNormalRange);
		labNormalRange.setTestMaster(null);

		return labNormalRange;
	}

	public List<UserTest> getUserTests() {
		return this.userTests;
	}

	public void setUserTests(List<UserTest> userTests) {
		this.userTests = userTests;
	}

	public UserTest addUserTest(UserTest userTest) {
		getUserTests().add(userTest);
		userTest.setTestMaster(this);

		return userTest;
	}

	public UserTest removeUserTest(UserTest userTest) {
		getUserTests().remove(userTest);
		userTest.setTestMaster(null);

		return userTest;
	}

}