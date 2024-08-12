package com.hope.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hope.doctor.model.ConsultationsOp;
import com.hope.doctor.model.OtpIp;
import com.hope.doctor.model.OtpOp;
import com.hope.doctor.model.SharingIp;
import com.hope.doctor.model.SharingOp;
import com.hope.doctor.service.DoctorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	DoctorService doctorservice;
	
	@GetMapping(value="allConsultations")
	public ConsultationsOp getConsultations() {
		ConsultationsOp sharingOp = doctorservice.getConsultations();
		return sharingOp;
	}
	
	@GetMapping(value="activeConsultations")
	public ConsultationsOp getActiveConsultations() {
		ConsultationsOp sharingOp = doctorservice.getActiveConsultations();
		return sharingOp;
	}
	
	@PostMapping(value="verifyOtp")
	public OtpOp verifyOtp(@RequestBody OtpIp otpIp) {
		OtpOp otpOp = doctorservice.verifyOtp(otpIp);
		return otpOp;
	}
	
	@PostMapping(value="unShareProfile")
	public SharingOp unShareProfile(@RequestBody SharingIp sharingIp) {
		SharingOp sharingOp = doctorservice.unShareProfile(sharingIp);
		return sharingOp;
	}
	
	
	
}
