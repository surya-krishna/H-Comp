package com.hope.root.service;

import java.util.List;

import com.hope.root.model.DeleteUserModel;
import com.hope.root.model.LabModel;
import com.hope.root.model.TestDataOp;
import com.hope.root.model.UserModel;
import com.hope.root.model.UserOp;
import com.hope.root.model.VerifyIp;
import com.hope.root.model.VerifyOp;

public interface RootService {

    UserOp save(UserModel user);

    List<UserModel> findAllUsers();

	UserOp deleteUser(DeleteUserModel user);

	UserOp editUser(UserModel user);

	VerifyOp verifyOTP(VerifyIp verifyIp);

	UserOp saveDoctor(UserModel user);
	
	UserOp saveLab(LabModel user);

	TestDataOp getTestData();
    
}
