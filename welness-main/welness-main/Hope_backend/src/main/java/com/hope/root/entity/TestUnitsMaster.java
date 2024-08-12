package com.hope.root.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the test_units_master database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="test_units_master")
@NamedQuery(name="TestUnitsMaster.findAll", query="SELECT t FROM TestUnitsMaster t")
public class TestUnitsMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="test_units_id")
	private int testUnitsId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="conversion_to_primary")
	private BigDecimal conversionToPrimary;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="is_primary")
	private int isPrimary;

	@Column(name="units_name")
	private String unitsName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to TestMaster
	@ManyToOne
	@JoinColumn(name="test_master_id")
	private TestMaster testMaster;

	//bi-directional many-to-one association to LabTest
	@OneToMany(mappedBy="testUnitsMaster")
	private List<LabTest> labTests;

	public TestUnitsMaster() {
	}

	public int getTestUnitsId() {
		return this.testUnitsId;
	}

	public void setTestUnitsId(int testUnitsId) {
		this.testUnitsId = testUnitsId;
	}

	public int getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public BigDecimal getConversionToPrimary() {
		return this.conversionToPrimary;
	}

	public void setConversionToPrimary(BigDecimal conversionToPrimary) {
		this.conversionToPrimary = conversionToPrimary;
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

	public int getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getUnitsName() {
		return this.unitsName;
	}

	public void setUnitsName(String unitsName) {
		this.unitsName = unitsName;
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

	public TestMaster getTestMaster() {
		return this.testMaster;
	}

	public void setTestMaster(TestMaster testMaster) {
		this.testMaster = testMaster;
	}

	public List<LabTest> getLabTests() {
		return this.labTests;
	}

	public void setLabTests(List<LabTest> labTests) {
		this.labTests = labTests;
	}

	public LabTest addLabTest(LabTest labTest) {
		getLabTests().add(labTest);
		labTest.setTestUnitsMaster(this);

		return labTest;
	}

	public LabTest removeLabTest(LabTest labTest) {
		getLabTests().remove(labTest);
		labTest.setTestUnitsMaster(null);

		return labTest;
	}

}