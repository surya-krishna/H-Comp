package com.hope.user.dao.impl;

import com.hope.root.config.Utility;
import com.hope.root.entity.*;
import com.hope.user.model.*;
import org.hibernate.query.Query;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hope.user.dao.UserDAO;

@Repository
@Transactional 
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	
	@Override
	public void saveReport(Report report) {
		Session session = sessionFactory.getCurrentSession();
		session.save(report);
		
	}
	

	@Override
	public void saveOrUpdateReport(Report report) {
		Session session = sessionFactory.getCurrentSession();
		session.update(report);
		
	}


	@Override
	public Report getReportDetails(Integer addReportId, long userId,String date) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Report where (reportId=:REPORTID or reportDate=:REPORTDATE) and user.userId=:USERID and activeStatus=:STATUS ");
		query.setParameter("REPORTID", addReportId);
		query.setParameter("REPORTDATE", date!=null?Utility.stringToDateWithFormat(date, "dd-MM-yyyy"):date);
		query.setParameter("USERID",(int) userId);
		query.setParameter("STATUS", 1);
		Report report = (Report) query.uniqueResult();
		return report;
	}
	
	@Override
	public List<Report> getReports(ReportListIP reportListIp,long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Report where user.userId=:USERID and activeStatus=:STATUS order by reportDate desc");
		query.setParameter("USERID",(int) userId);
		query.setParameter("STATUS", 1);
		query.setFirstResult(reportListIp.getLowerLimit());
		query.setMaxResults(reportListIp.getCount());
		List<Report> reports = (List<Report>) query.list();
		return reports;
	}


	@Override
	public List<UserTest> getTestDetails(String testMasterName, long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserTest where user.userId=:USERID and testName=:TESTNAME and activeStatus=:STATUS ");
		query.setParameter("USERID",(int) userId);
		query.setParameter("TESTNAME", testMasterName);
		query.setParameter("STATUS", 1);
		List<UserTest> userTest = (List<UserTest>) query.list();
		return userTest;
	}


	@Override
	public List<Object[]> getTestList(TestListIP testListIp,long userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(userTestId),testName,testResultType, "
				+ " avg(testValue),avg(testMaxNormal),avg(testMinNormal)"
				+ " from UserTest where user.userId=:USERID and activeStatus=:STATUS group by testName,testResultType order by count(userTestId) desc ");
		query.setParameter("USERID",(int) userId);
		query.setParameter("STATUS", 1);
		query.setFirstResult(testListIp.getLowerLimit());
		query.setMaxResults(testListIp.getCount());
		List<Object[]> userTest = (List<Object[]>) query.list();
		return userTest;
	}

	

	
	public User getDoctor(SharingIp sharingIp){
		Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from User where userName=:username ");
			query.setParameter("username", sharingIp.getDoctorEmail());
			User user=(User)query.uniqueResult();
			return user;		
	}

	public SharingDetail saveSharingDetails(SharingDetail sharingDetail){
		Session session = sessionFactory.getCurrentSession();
		session.save(sharingDetail);
		return sharingDetail;
	}


	@Override
	public List<SharingDetail> getConsultations(String string, long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		if(string.equals("All")) {
			Sql+="from SharingDetail where user.userId=:USERID and (activeStatus=0)";
		}
		else {
			Sql+="from SharingDetail where user.userId=:USERID and activeStatus=1";	
		}
		Query query=session.createQuery(Sql);
		query.setParameter("USERID",(int)userId);
		List<SharingDetail> shareList=(List<SharingDetail>)query.list();
		return shareList;		
	}


	@Override
	public SharingDetail getSharingDetail(int sharingId,long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from SharingDetail where sharingId=:CONSULTATIONID and accessedOn is not null and doctor.userId=:DOCTORID and activeStatus=1";
		Query query=session.createQuery(Sql);
		query.setParameter("CONSULTATIONID",sharingId);
		query.setParameter("DOCTORID",(int)userId);
		SharingDetail share=(SharingDetail)query.uniqueResult();
		return share;		
	}


	@Override
	public SharingDetail getUserSharingDetail(int sharingId, long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from SharingDetail where sharingId=:CONSULTATIONID  and user.userId=:USERID and activeStatus=1";
		Query query=session.createQuery(Sql);
		query.setParameter("CONSULTATIONID",sharingId);
		query.setParameter("USERID",(int)userId);
		SharingDetail share=(SharingDetail)query.uniqueResult();
		return share;
	}


	@Override
	public void updateSharingDetails(SharingDetail sd) {
		Session session = sessionFactory.getCurrentSession();
		session.save(sd);
		return;
		
	}

	@Override
	public List<Number> getStepCount(long userId) {
		return null;
	}

	@Override
	public GoogleCredentials addGoogleCredentials(GoogleCredentials credentials) {
		Session session = sessionFactory.getCurrentSession();
		session.save(credentials);
		return credentials;
	}

	@Override
	public GoogleCredentials getGoogleCredentials(long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from GoogleCredentials where user.userId=:USERID order by id desc";
		Query query=session.createQuery(Sql);
		query.setParameter("USERID",(int)userId);
		return (GoogleCredentials)query.list().get(0);
	}


	@Override
	public List<Object[]> getMedicalSummary(long userId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createNativeQuery("select count(user_test_id),test_name,test_result_type,"
				+ "				 avg(test_value),avg(test_max_normal),avg(test_min_normal),variance(test_value),GROUP_CONCAT(observation SEPARATOR '. '),GROUP_CONCAT(suggestions SEPARATOR '. ')"
				+ "				from user_tests ut, users u where u.user_id=ut.user_id and u.user_id=:USERID and ut.active_status=:STATUS group by test_name,test_result_type order by count(user_test_id) desc");
		query.setParameter("USERID",(int) userId);
		query.setParameter("STATUS", 1);
		List<Object[]> userTest = (List<Object[]>) query.list();
		return userTest;
		
	}


	@Override
	public List<Object[]> getRecentTests(long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select u.testName, u.testValue, u.units, u.testMinNormal, u.testMaxNormal, u.observation, u.suggestions, r.reportDate " +
	             "from UserTest u join u.report r " +
	             "where u.activeStatus = 1 " +
	             "and u.testName in (select distinct(ut.testName) from UserTest ut) " +
	             "and r.reportDate = (select max(rt.reportDate) " +
	             "                   from UserTest ut2 join ut2.report rt " +
	             "                   where ut2.testName = u.testName) and u.user.userId=:USERID");
		query.setParameter("USERID",(int) userId);
		List<Object[]> userTest = (List<Object[]>) query.list();
		return userTest;
	}
}
