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
 * The persistent class for the test_files database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="test_files")
@NamedQuery(name="TestFile.findAll", query="SELECT t FROM TestFile t")
public class TestFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="test_file_id")
	private int testFileId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="test_file_alt")
	private String testFileAlt;

	@Column(name="test_file_path")
	private String testFilePath;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to UserTest
	@ManyToOne
	@JoinColumn(name="user_test_id")
	private UserTest userTest;

	public TestFile() {
	}

	public int getTestFileId() {
		return this.testFileId;
	}

	public void setTestFileId(int testFileId) {
		this.testFileId = testFileId;
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

	public String getTestFileAlt() {
		return this.testFileAlt;
	}

	public void setTestFileAlt(String testFileAlt) {
		this.testFileAlt = testFileAlt;
	}

	public String getTestFilePath() {
		return this.testFilePath;
	}

	public void setTestFilePath(String testFilePath) {
		this.testFilePath = testFilePath;
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

	public UserTest getUserTest() {
		return this.userTest;
	}

	public void setUserTest(UserTest userTest) {
		this.userTest = userTest;
	}

}