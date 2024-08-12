package com.hope.root.dao;



import java.util.List;

import com.hope.root.entity.LabDetail;
import com.hope.root.entity.TestMaster;
import com.hope.root.entity.User;


public interface RootDao{
    
	User findByUsername(String userName);

	User save(User newUser);
	
	User saveOrUpdate(User newUser);

	List<User> findAll();

	User findById(int userId);

	LabDetail saveLab(LabDetail labDetail);

	List<TestMaster> getTestMaster();
}
