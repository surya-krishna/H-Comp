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
 * The persistent class for the doctor_details database table.
 * 
 */

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="doctor_details")
@NamedQuery(name="DoctorDetail.findAll", query="SELECT d FROM DoctorDetail d")
public class DoctorDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hospital_doctor_id")
	private int hospitalDoctorId;

	@Column(name="active_status")
	private int activeStatus;

	private String address;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="current_receipt_number")
	private int currentReceiptNumber;

	@Column(name="hospital_name")
	private String hospitalName;

	@Column(name="hospital_number")
	private String hospitalNumber;

	private String specialization;

	private String timings;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private User user;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="doctorDetail")
	private List<Prescription> prescriptions;

	public DoctorDetail() {
	}

	public int getHospitalDoctorId() {
		return this.hospitalDoctorId;
	}

	public void setHospitalDoctorId(int hospitalDoctorId) {
		this.hospitalDoctorId = hospitalDoctorId;
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

	public String getHospitalName() {
		return this.hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalNumber() {
		return this.hospitalNumber;
	}

	public void setHospitalNumber(String hospitalNumber) {
		this.hospitalNumber = hospitalNumber;
	}

	public String getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Prescription addPrescription(Prescription prescription) {
		getPrescriptions().add(prescription);
		prescription.setDoctorDetail(this);

		return prescription;
	}

	public Prescription removePrescription(Prescription prescription) {
		getPrescriptions().remove(prescription);
		prescription.setDoctorDetail(null);

		return prescription;
	}

}