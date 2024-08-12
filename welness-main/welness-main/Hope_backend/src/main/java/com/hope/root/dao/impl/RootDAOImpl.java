package com.hope.root.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hope.root.dao.RootDao;
import com.hope.root.entity.LabDetail;
import com.hope.root.entity.TestMaster;
import com.hope.root.entity.User;
@Repository
public class RootDAOImpl implements RootDao{


	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = LoggerFactory.getLogger(RootDAOImpl.class);
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	@Override
	public User findByUsername(String userName) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from User where userName=:username ");
			query.setParameter("username", userName);
			User user=(User)query.uniqueResult();
			return user;
	}

	@Override
	public User save(User newUser) {
		Session session=sessionFactory.getCurrentSession();
		session.save(newUser);
		return newUser;
	}
	
	@Override
	public User saveOrUpdate(User newUser) {
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(newUser);
		return newUser;
	}

	@Override
	public List<User> findAll() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where activeStatus=:activeStatus  ");
		query.setParameter("activeStatus", 1);
		List<User> user=(List<User>)query.list();
		return user;
		
	}

	@Override
	public User findById(int userId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User where userId=:userId activeStatus=:activeStatus  ");
		query.setParameter("userId", userId);
		query.setParameter("activeStatus", 1);
		User user=(User)query.uniqueResult();
		return user;

	}

	@Override
	public LabDetail saveLab(LabDetail labDetail) {
		Session session=sessionFactory.getCurrentSession();
		session.save(labDetail);
		return labDetail;
	}

	@Override
	public List<TestMaster> getTestMaster() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from TestMaster where activeStatus=:activeStatus");
		query.setParameter("activeStatus", 1);
		List<TestMaster> testMasterList=query.list();
		return testMasterList;

	}

}
