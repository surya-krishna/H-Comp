package com.hope.doctor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultationModel {
	private int consultationId;
	private String userName;
	private String consultationDate;
	private String status;
	private String otp;
}
