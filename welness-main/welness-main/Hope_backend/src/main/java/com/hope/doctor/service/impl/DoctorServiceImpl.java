package com.hope.doctor.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hope.doctor.dao.DoctorDAO;
import com.hope.doctor.model.ConsultationModel;
import com.hope.doctor.model.ConsultationsOp;
import com.hope.doctor.model.OtpIp;
import com.hope.doctor.model.OtpOp;
import com.hope.doctor.model.SharingIp;
import com.hope.doctor.model.SharingOp;
import com.hope.doctor.service.DoctorService;
import com.hope.root.config.Utility;
import com.hope.root.entity.SharingDetail;
import com.hope.root.model.UserDetails;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{
	@Autowired
	DoctorDAO doctorDAO;
	
	@Override
	public ConsultationsOp getConsultations() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId = userDetails.getUserId();
		List<SharingDetail> sharing = doctorDAO.getConsultations("All",userId);
		List<ConsultationModel> consultations=new ArrayList<>();
		for(SharingDetail share:sharing) {
			ConsultationModel cm= new ConsultationModel();
			cm.setConsultationDate(Utility.dateToStringWithFormat(share.getCreatedTime(),"yyyy-MM-dd"));
			cm.setConsultationId(share.getSharingId());
			String userEmail=share.getUser().getUserName();
			int lastInd = userEmail.lastIndexOf("@");
			int half=lastInd/2;
			String X="";
			for(int i=half+1;i<=lastInd;i++) {
				X+="X";
			}
			String userFinal = userEmail.substring(0,half+1)+X+userEmail.substring(lastInd+1);
			cm.setUserName(userFinal);
			cm.setOtp(share.getOtpNumber()+"");
			consultations.add(cm);
		}
		ConsultationsOp cop = new ConsultationsOp();
		cop.setConsultationList(consultations);
		return cop;
	}


	@Override
	public ConsultationsOp getActiveConsultations() {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId = userDetails.getUserId();
		List<SharingDetail> sharing = doctorDAO.getConsultations("Active",userId);
		List<ConsultationModel> consultations=new ArrayList<>();
		for(SharingDetail share:sharing) {
			ConsultationModel cm= new ConsultationModel();
			cm.setConsultationDate(Utility.dateToStringWithFormat(share.getCreatedTime(),"yyyy-MM-dd"));
			cm.setStatus(share.getAccessedOn()!=null?"Verified":"");
			cm.setConsultationId(share.getSharingId());
			String userEmail=share.getUser().getUserName();
			int lastInd = userEmail.lastIndexOf("@");
			int half=lastInd/2;
			String X="";
			for(int i=half+1;i<=lastInd;i++) {
				X+="X";
			}
			String userFinal = userEmail.substring(0,half+1)+X+userEmail.substring(lastInd+1);
			cm.setUserName(userFinal);
			consultations.add(cm);
		}
		ConsultationsOp cop = new ConsultationsOp();
		cop.setConsultationList(consultations);
		return cop;
		
	}


	@Override
	public OtpOp verifyOtp(OtpIp otpIp) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SharingDetail sd= doctorDAO.getConsultation(otpIp.getConsultationId());
		Timestamp curTime = new Timestamp(new Date().getTime());		
		OtpOp otpOp=new OtpOp();
		otpOp.setConsultationId(sd.getSharingId());
		if(sd.getOtpNumber()==otpIp.getOtp()&&sd.getDoctor().getUserId()==userDetails.getUserId()) {
			SharingDetail sdA= doctorDAO.getLastActive((int)userDetails.getUserId());
			if(sdA!=null) {
			sdA.setActiveStatus(0);
			doctorDAO.updateSharingDetails(sdA);
			}
			sd.setAccessedOn(curTime);
			doctorDAO.updateSharingDetails(sd);

			otpOp.setErrFlag(0);
			otpOp.setMsg("Success");
			return otpOp;
		}
		else {
			otpOp.setErrFlag(1);
			otpOp.setMsg("Wrong OTP");
			return otpOp;
		}
		
	}
	
	@Override
	public SharingOp unShareProfile(SharingIp sharingIp) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Timestamp curTime = new Timestamp(new Date().getTime());
		long userId = userDetails.getUserId();
		SharingDetail sd=doctorDAO.getUserSharingDetail(sharingIp.getSharingId(), userId);
		sd.setActiveStatus(0);
		sd.setUpdatedBy(userId+"");
		sd.setUpdatedTime(curTime);
		doctorDAO.updateSharingDetails(sd);
		SharingOp sop = new SharingOp();
		sop.setActiveFlag(1);
		sop.setErrFlag(0);
		return sop;
	}
}
