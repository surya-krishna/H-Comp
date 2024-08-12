package com.hope.root.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the prescription database table.
 * 
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NamedQuery(name="Prescription.findAll", query="SELECT p FROM Prescription p")
public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prescription_id")
	private int prescriptionId;

	@Column(name="active_status")
	private int activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="doctor_receipt_number")
	private int doctorReceiptNumber;

	@Column(name="overall_observation")
	private String overallObservation;

	@Temporal(TemporalType.DATE)
	@Column(name="prescription_date")
	private Date prescriptionDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private Timestamp updatedTime;

	//bi-directional many-to-one association to DoctorDetail
	@ManyToOne
	@JoinColumn(name="hospital_doctor_id")
	private DoctorDetail doctorDetail;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="doctor_id")
	private User doctor;

	//bi-directional many-to-one association to Observation
	@OneToMany(mappedBy="prescription")
	//@Singular("medication")
	private List<Medication> medication;

	public Prescription() {
	}

	public int getPrescriptionId() {
		return this.prescriptionId;
	}

	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
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

	public int getDoctorReceiptNumber() {
		return this.doctorReceiptNumber;
	}

	public void setDoctorReceiptNumber(int doctorReceiptNumber) {
		this.doctorReceiptNumber = doctorReceiptNumber;
	}

	public String getOverallObservation() {
		return this.overallObservation;
	}

	public void setOverallObservation(String overallObservation) {
		this.overallObservation = overallObservation;
	}

	public Date getPrescriptionDate() {
		return this.prescriptionDate;
	}

	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
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

	public DoctorDetail getDoctorDetail() {
		return this.doctorDetail;
	}

	public void setDoctorDetail(DoctorDetail doctorDetail) {
		this.doctorDetail = doctorDetail;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public List<Medication> getObservations() {
		return this.medication;
	}

	public void setObservations(List<Medication> medication) {
		this.medication = medication;
	}

	public Medication addObservation(Medication medication) {
		getObservations().add(medication);
		medication.setPrescription(this);

		return medication;
	}

	public Medication removeObservation(Medication medication) {
		getObservations().remove(medication);
		medication.setPrescription(null);

		return medication;
	}

	


}