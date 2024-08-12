package com.hope.user.service;

import java.util.Map;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import com.hope.user.model.*;

public interface UserService {

    ReportStatusOP addReport(AddReportIP addReportIp);

    ReportStatusOP editReport(AddReportIP addReportIp);

    ReportStatusOP deleteReport(DeleteReportIP deleteReportIp);

    TestListOP getTestList(TestListIP testListIp);

    ReportDetailsOP getReportDetails(ReportDetailIP reportDetailIp);

    ReportListOP getReportList(ReportListIP reportListIp);

    TestDetailsOP getTestDetails(TestDetailsIP testDetailsIp);

    Map extractData(GeminiIp geminiIp);

    Map uploadFileCapture(GeminiIp geminiIp);

    String getMedicalSummary(int consultationId,SimpMessageHeaderAccessor headerAccessor);

    SharingOp shareProfile(SharingIp sharingIp);

    ConsultationsOp getConsultations();

    ConsultationsOp getActiveConsultations();

    SharingOp unShareProfile(SharingIp sharingIp);


    Map getStepCount(ActivityIp aip);

    Map getNutrition(ActivityIp aip);

    void addGoogleCredentials(GoogleCredentialsIP credentials);

	Map deleteFileCapture(GeminiIp geminiIp);

	String getMedicalSummaryDetails(int consultation_id);

	String getRecentTestValues(int consultation_id);

}
