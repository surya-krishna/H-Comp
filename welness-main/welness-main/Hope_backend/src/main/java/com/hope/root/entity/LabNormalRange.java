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
 * The persistent class for the lab_normal_ranges database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="lab_normal_ranges")
@NamedQuery(name="LabNormalRange.findAll", query="SELECT l FROM LabNormalRange l")
public class LabNormalRange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lab_normal_ranges_id")
	private int labNormalRangesId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="age_max")
	private int ageMax;

	@Column(name="age_min")
	private int ageMin;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	private String gender;

	@Column(name="other_spec_max")
	private int otherSpecMax;

	@Column(name="other_spec_min")
	private int otherSpecMin;

	@Column(name="preson_condition")
	private String presonCondition;

	@Column(name="specification_name")
	private String specificationName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to LabTest
	@ManyToOne
	@JoinColumn(name="lab_test_id")
	private LabTest labTest;

	//bi-directional many-to-one association to TestMaster
	@ManyToOne
	@JoinColumn(name="test_master_id")
	private TestMaster testMaster;

	public LabNormalRange() {
	}

	public int getLabNormalRangesId() {
		return this.labNormalRangesId;
	}

	public void setLabNormalRangesId(int labNormalRangesId) {
		this.labNormalRangesId = labNormalRangesId;
	}

	public int getActiveStatus() {
		return this.activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public int getAgeMax() {
		return this.ageMax;
	}

	public void setAgeMax(int ageMax) {
		this.ageMax = ageMax;
	}

	public int getAgeMin() {
		return this.ageMin;
	}

	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
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

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getOtherSpecMax() {
		return this.otherSpecMax;
	}

	public void setOtherSpecMax(int otherSpecMax) {
		this.otherSpecMax = otherSpecMax;
	}

	public int getOtherSpecMin() {
		return this.otherSpecMin;
	}

	public void setOtherSpecMin(int otherSpecMin) {
		this.otherSpecMin = otherSpecMin;
	}

	public String getPresonCondition() {
		return this.presonCondition;
	}

	public void setPresonCondition(String presonCondition) {
		this.presonCondition = presonCondition;
	}

	public String getSpecificationName() {
		return this.specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
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

	public LabTest getLabTest() {
		return this.labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}

	public TestMaster getTestMaster() {
		return this.testMaster;
	}

	public void setTestMaster(TestMaster testMaster) {
		this.testMaster = testMaster;
	}

}