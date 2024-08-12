package com.hope.doctor.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hope.doctor.dao.DoctorDAO;
import com.hope.root.entity.SharingDetail;
import com.hope.user.dao.UserDAO;
import com.hope.user.dao.impl.UserDAOImpl;

@Repository
public class DoctorDAOImpl implements DoctorDAO {


	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = LoggerFactory.getLogger(DoctorDAOImpl.class);

	@Override
	public List<SharingDetail> getConsultations(String string, long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		if(string.equals("All")) {
			Sql+="from SharingDetail where doctor.userId=:USERID and (activeStatus=0)";
		}
		else {
			Sql+="from SharingDetail where doctor.userId=:USERID and activeStatus=1";	
		}
		Query query=session.createQuery(Sql);
		query.setParameter("USERID",(int)userId);
		List<SharingDetail> shareList=(List<SharingDetail>)query.list();
		return shareList;		
	}

	@Override
	public SharingDetail getConsultation(int consultationId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from SharingDetail where sharingId=:CONSULTATIONID and activeStatus=1";
		
		Query query=session.createQuery(Sql);
		query.setParameter("CONSULTATIONID",consultationId);
		SharingDetail share=(SharingDetail)query.uniqueResult();
		return share;		
	}

	@Override
	public SharingDetail getLastActive(int doctorId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from SharingDetail where doctor.userId=:DOCTORID and accessedOn is not null and activeStatus=1";
		Query query=session.createQuery(Sql);
		query.setParameter("DOCTORID",doctorId);
		SharingDetail share=(SharingDetail)query.uniqueResult();
		return share;		
	}

	
	@Override
	public void updateSharingDetails(SharingDetail sharingDetail) {
		Session session = sessionFactory.getCurrentSession();
		session.update(sharingDetail);
		return;
		
	}
	
	@Override
	public SharingDetail getUserSharingDetail(int sharingId, long userId) {
		Session session=sessionFactory.getCurrentSession();
		String Sql="";
		Sql+="from SharingDetail where sharingId=:CONSULTATIONID  and doctor.userId=:USERID and activeStatus=1";
		Query query=session.createQuery(Sql);
		query.setParameter("CONSULTATIONID",sharingId);
		query.setParameter("USERID",(int)userId);
		SharingDetail share=(SharingDetail)query.uniqueResult();
		return share;
	}

}
