package com.hope.user.dao;

import java.util.List;

import com.hope.root.entity.*;
import com.hope.user.model.*;

public interface UserDAO {

	void saveReport(Report report);

	Report getReportDetails(Integer integer, long userId,String date);

	void saveOrUpdateReport(Report report);

	List<Report> getReports(ReportListIP reportListIp, long userId);

	List<UserTest> getTestDetails(String string, long userId);

	List<Object[]> getTestList(TestListIP testListIp, long userId);

	User getDoctor(SharingIp sharingIp);

	SharingDetail saveSharingDetails(SharingDetail sharingDetail);

	List<SharingDetail> getConsultations(String string, long userId);

	SharingDetail getSharingDetail(int sharingId, long l);

	SharingDetail getUserSharingDetail(int sharingId, long userId);

	void updateSharingDetails(SharingDetail sd);

    List<Number> getStepCount(long userId);

    GoogleCredentials addGoogleCredentials(GoogleCredentials credentials);

    GoogleCredentials getGoogleCredentials(long userId);
    
    List<Object[]> getMedicalSummary(long userId);

	List<Object[]> getRecentTests(long userId);

}
