package com.hope.doctor.service;

import com.hope.doctor.model.ConsultationsOp;
import com.hope.doctor.model.OtpIp;
import com.hope.doctor.model.OtpOp;
import com.hope.doctor.model.SharingIp;
import com.hope.doctor.model.SharingOp;

public interface DoctorService {

	ConsultationsOp getConsultations();

	ConsultationsOp getActiveConsultations();

	OtpOp verifyOtp(OtpIp otpIp);

	SharingOp unShareProfile(SharingIp sharingIp);

}
