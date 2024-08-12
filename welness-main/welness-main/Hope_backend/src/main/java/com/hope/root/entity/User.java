package com.hope.root.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the users database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dateOfBirth;

	@Column(name="doctor_reg_id")
	private String doctorRegId;

	@Column(name="email_verification")
	private String emailVerification;

	private String gender;

	private String password;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mobile_number")
	private String mobileNumber;

	//bi-directional many-to-one association to LabDetail
	@ManyToOne
	@JoinColumn(name="lab_id")
	private LabDetail labDetail;

	//bi-directional many-to-one association to DoctorDetail
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<DoctorDetail> doctorDetails;

	//bi-directional one-to-one association to UserOtp
	@OneToOne(mappedBy="user",cascade = CascadeType.ALL)
	private UserOtp userOtp;

	//bi-directional one-to-one association to SharingDetail
	@OneToMany(mappedBy="user")
	private List<SharingDetail> userSharingDetail;

	//bi-directional many-to-one association to SharingDetail
	@OneToMany(mappedBy="doctor")
	private List<SharingDetail> doctorSharingDetails;

	//bi-directional many-to-one association to Report
	@OneToMany(mappedBy="user")
	private List<Report> userReports;

	//bi-directional many-to-one association to Report
	@OneToMany(mappedBy="operator")
	private List<Report> operatorReports;

	//bi-directional many-to-one association to Report
	@OneToMany(mappedBy="incharge")
	private List<Report> inchargeReports;

	//bi-directional many-to-one association to UserTest
	@OneToMany(mappedBy="user")
	private List<UserTest> userTests;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="user")
	//@Singular("userPrescription")
	private List<Prescription> userPrescription;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="doctor")
	//@Singular("doctorPrescription")
	private List<Prescription> doctorPrescription;

	//bi-directional many-to-one association to Observation
	@OneToMany(mappedBy="user")
	//@Singular("medication")
	private List<Medication> medication;

	//bi-directional many-to-one association to RolesMaster
	@ManyToOne
	@JoinColumn(name="role_id")
	private RolesMaster rolesMaster;
	

	@Column(name="active_status")
	private int activeStatus;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDoctorRegId() {
		return this.doctorRegId;
	}

	public void setDoctorRegId(String doctorRegId) {
		this.doctorRegId = doctorRegId;
	}

	public String getEmailVerification() {
		return this.emailVerification;
	}

	public void setEmailVerification(String emailVerification) {
		this.emailVerification = emailVerification;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LabDetail getLabDetail() {
		return this.labDetail;
	}

	public void setLabDetail(LabDetail labDetail) {
		this.labDetail = labDetail;
	}

	public List<DoctorDetail> getDoctorDetails() {
		return this.doctorDetails;
	}

	public void setDoctorDetails(List<DoctorDetail> doctorDetails) {
		this.doctorDetails = doctorDetails;
	}

	public DoctorDetail addDoctorDetail(DoctorDetail doctorDetail) {
		getDoctorDetails().add(doctorDetail);
		doctorDetail.setUser(this);

		return doctorDetail;
	}

	public DoctorDetail removeDoctorDetail(DoctorDetail doctorDetail) {
		getDoctorDetails().remove(doctorDetail);
		doctorDetail.setUser(null);

		return doctorDetail;
	}

	public UserOtp getUserOtp() {
		return this.userOtp;
	}

	public void setUserOtp(UserOtp userOtp) {
		this.userOtp = userOtp;
	}


	public List<UserTest> getUserTests() {
		return this.userTests;
	}

	public void setUserTests(List<UserTest> userTests) {
		this.userTests = userTests;
	}

	public UserTest addUserTest(UserTest userTest) {
		getUserTests().add(userTest);
		userTest.setUser(this);

		return userTest;
	}

	public UserTest removeUserTest(UserTest userTest) {
		getUserTests().remove(userTest);
		userTest.setUser(null);

		return userTest;
	}

	public RolesMaster getRolesMaster() {
		return this.rolesMaster;
	}

	public void setRolesMaster(RolesMaster rolesMaster) {
		this.rolesMaster = rolesMaster;
	}

	public List<SharingDetail> getUserSharingDetail() {
		return userSharingDetail;
	}

	public void setUserSharingDetail(List<SharingDetail> userSharingDetail) {
		this.userSharingDetail = userSharingDetail;
	}

	public List<SharingDetail> getDoctorSharingDetails() {
		return doctorSharingDetails;
	}

	public void setDoctorSharingDetails(List<SharingDetail> doctorSharingDetails) {
		this.doctorSharingDetails = doctorSharingDetails;
	}

	public List<Report> getUserReports() {
		return userReports;
	}

	public void setUserReports(List<Report> userReports) {
		this.userReports = userReports;
	}

	public List<Report> getOperatorReports() {
		return operatorReports;
	}

	public void setOperatorReports(List<Report> operatorReports) {
		this.operatorReports = operatorReports;
	}

	public List<Report> getInchargeReports() {
		return inchargeReports;
	}

	public void setInchargeReports(List<Report> inchargeReports) {
		this.inchargeReports = inchargeReports;
	}

	public List<Prescription> getUserPrescription() {
		return userPrescription;
	}

	public void setUserPrescription(List<Prescription> userPrescription) {
		this.userPrescription = userPrescription;
	}

	public List<Prescription> getDoctorPrescription() {
		return doctorPrescription;
	}

	public void setDoctorPrescription(List<Prescription> doctorPrescription) {
		this.doctorPrescription = doctorPrescription;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public List<Medication> getmedications() {
		return this.medication;
	}

	public void setmedications(List<Medication> medications) {
		this.medication = medications;
	}

	public Medication addmedication(Medication medication) {
		getmedications().add(medication);
		medication.setUser(this);

		return medication;
	}

	public Medication removemedication(Medication medication) {
		getmedications().remove(medication);
		medication.setUser(null);

		return medication;
	}

	
	

}