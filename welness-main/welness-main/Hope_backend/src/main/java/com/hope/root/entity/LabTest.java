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
 * The persistent class for the lab_tests database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="lab_tests")
@NamedQuery(name="LabTest.findAll", query="SELECT l FROM LabTest l")
public class LabTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lab_test_id")
	private int labTestId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="prefered_code")
	private String preferedCode;

	@Column(name="prefered_name")
	private String preferedName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to TestMaster
	@ManyToOne
	@JoinColumn(name="test_master_id")
	private TestMaster testMaster;

	//bi-directional many-to-one association to TestUnitsMaster
	@ManyToOne
	@JoinColumn(name="test_units_id")
	private TestUnitsMaster testUnitsMaster;

	//bi-directional many-to-one association to LabDetail
	@ManyToOne
	@JoinColumn(name="lab_id")
	private LabDetail labDetail;

	//bi-directional many-to-one association to LabNormalRange
	@OneToMany(mappedBy="labTest")
	private List<LabNormalRange> labNormalRanges;

	public LabTest() {
	}

	public int getLabTestId() {
		return this.labTestId;
	}

	public void setLabTestId(int labTestId) {
		this.labTestId = labTestId;
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

	public String getPreferedCode() {
		return this.preferedCode;
	}

	public void setPreferedCode(String preferedCode) {
		this.preferedCode = preferedCode;
	}

	public String getPreferedName() {
		return this.preferedName;
	}

	public void setPreferedName(String preferedName) {
		this.preferedName = preferedName;
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

	public TestUnitsMaster getTestUnitsMaster() {
		return this.testUnitsMaster;
	}

	public void setTestUnitsMaster(TestUnitsMaster testUnitsMaster) {
		this.testUnitsMaster = testUnitsMaster;
	}

	public LabDetail getLabDetail() {
		return this.labDetail;
	}

	public void setLabDetail(LabDetail labDetail) {
		this.labDetail = labDetail;
	}

	public List<LabNormalRange> getLabNormalRanges() {
		return this.labNormalRanges;
	}

	public void setLabNormalRanges(List<LabNormalRange> labNormalRanges) {
		this.labNormalRanges = labNormalRanges;
	}

	public LabNormalRange addLabNormalRange(LabNormalRange labNormalRange) {
		getLabNormalRanges().add(labNormalRange);
		labNormalRange.setLabTest(this);

		return labNormalRange;
	}

	public LabNormalRange removeLabNormalRange(LabNormalRange labNormalRange) {
		getLabNormalRanges().remove(labNormalRange);
		labNormalRange.setLabTest(null);

		return labNormalRange;
	}

}