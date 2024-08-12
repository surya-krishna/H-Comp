package com.hope.root.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hope.root.config.Utility;
import com.hope.root.dao.RootDao;
import com.hope.root.entity.LabDetail;
import com.hope.root.entity.RolesMaster;
import com.hope.root.entity.TestMaster;
import com.hope.root.entity.TestMasterName;
import com.hope.root.entity.TestUnitsMaster;
import com.hope.root.entity.User;
import com.hope.root.entity.UserOtp;
import com.hope.root.model.Constants;
import com.hope.root.model.DeleteUserModel;
import com.hope.root.model.EmailDetails;
import com.hope.root.model.LabModel;
import com.hope.root.model.TestDataModel;
import com.hope.root.model.TestDataOp;
import com.hope.root.model.TestNamesModel;
import com.hope.root.model.TestUnitsModel;
import com.hope.root.model.UserDetails;
import com.hope.root.model.UserModel;
import com.hope.root.model.UserOp;
import com.hope.root.model.UserRole;
import com.hope.root.model.VerifyIp;
import com.hope.root.model.VerifyOp;
import com.hope.root.service.RootService;

@Service(value = "userService")
@Transactional
public class RootServiceImpl implements UserDetailsService, RootService {

	@Autowired
	private RootDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

    @Autowired 
    private JavaMailSender javaMailSender;
    

	Logger logger = LoggerFactory.getLogger(RootServiceImpl.class);
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * logger.info("Start of getUserDetails ( " + userName +
	 * " ) in SecurityDaoImpl");
	 * 
	 * User user = loadUser(userName.toLowerCase());
	 * 
	 * UserInformation userInfo = new UserInformation();
	 * 
	 * userInfo.setUserId(user.getUserId());
	 * userInfo.setUsername(user.getUserName().toLowerCase());
	 * userInfo.setLastLoginDate(user.getLastLoginTime());
	 * userInfo.setUserAvtive(new Boolean(user.getUserStatus()));
	 * userInfo.setSmsVerified(new Boolean(user.getSmsVerification()));
	 * userInfo.setEmailVerified(new Boolean(user.getEmailVerification()));
	 * userInfo.setWatchListFlag(user.getWatchListFlag());
	 * 
	 * userInfo.setPassword(user.getUserPassword());
	 * logger.info("User Password1 :: :: " + user.getUserPassword());
	 * logger.info("User Password2 :: :: " + getMD5Password());
	 * userInfo.setAccountNonExpired(true); userInfo.setCredentialsNonExpired(true);
	 * userInfo.setAccountNonLocked(true); userInfo.setEnabled(new Boolean(new
	 * Boolean(user.getUserStatus())));
	 * 
	 * UserRole role = new UserRole(); List<UserPriviliges> permissionsList = new
	 * ArrayList<UserPriviliges>(); List<String> priviligesList = new
	 * ArrayList<String>();
	 * 
	 * role.setRoleId(user.getRolesMaster().getRoleId() + "");
	 * role.setRoleName(user.getRolesMaster().getRoleName()); List<RolesPrivilige>
	 * rolePrivList = user.getRolesMaster() .getRolesPriviliges();
	 * 
	 * for (RolesPrivilige rolePriv : rolePrivList) { UserPriviliges priviliges =
	 * new UserPriviliges();
	 * priviliges.setId(rolePriv.getPriviligesMaster().getPriviligeId() + "");
	 * priviliges.setName(rolePriv.getPriviligesMaster() .getPriviligeName());
	 * priviligesList.add(rolePriv.getPriviligesMaster() .getPriviligeName());
	 * permissionsList.add(priviliges); }
	 * 
	 * role.setPermList(permissionsList);
	 * 
	 * List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	 * authorities.add(role);
	 * 
	 * userInfo.setPermissionList(priviligesList);
	 * userInfo.setAuthorities(authorities);
	 * 
	 * logger.info("End of getUserDetails ( ) in SecurityDaoImpl " +
	 * userInfo.getUserId());
	 * 
	 * return userInfo;
	 * 
	 * 
	 * 
	 * 
	 */

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		logger.info(user.getPassword());
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		else if(!user.getEmailVerification().equals("Y")) {
			throw new UsernameNotFoundException("Email Not Verified");
		}

		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(user.getUserName());
		userDetails.setUserId(user.getUserId());
		userDetails.setPassword(user.getPassword());
		userDetails.setAccountNonExpired(true);
		userDetails.setCredentialsNonExpired(true);
		userDetails.setAccountNonLocked(true);
		userDetails.setEnabled(true);
		userDetails.setRole(user.getRolesMaster().getRoleName());

		RolesMaster roles = user.getRolesMaster();

		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

		UserRole userRole = new UserRole();
		userRole.setRoleId(roles.getRoleId());
		userRole.setRoleName(roles.getRoleName());
		authorityList.add(userRole);

		userDetails.setAuthorities(authorityList);

		return userDetails;
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		// authorities.add(new SimpleGrantedAuthority(role.getName()));
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRolesMaster().getRoleName()));
		return authorities;
		// return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	/*
	 * public List<User> findAll() { List<User> list = new ArrayList<>();
	 * userDao.findAll().iterator().forEachRemaining(list::add); return list; }
	 * 
	 * @Override public void delete(long id) { userDao.deleteById(id); }
	 * 
	 * @Override public User findOne(String username) { return
	 * userDao.findByUsername(username); }
	 * 
	 * @Override public User findById(Long id) { return userDao.findById(id).get();
	 * }
	 */

	@Override
	public UserOp save(UserModel user) {
		UserOp validated = validateUser(user);
		if(validated!=null)
			return validated;
		User newUser = saveUserDetails(user);
		User userSaved = userDao.save(newUser);
		UserOp userop = new UserOp();
		if (userSaved.getUserId() != 0) {
			userop.setErrFlag(0);
			userop.setMsg("Success");
		} else {
			userop.setErrFlag(0);
			userop.setMsg("Some Error Occured");
		}
		return userop;
	}
	
	public UserOp validateUser(UserModel user) {
		User existuser = userDao.findByUsername(user.getEmailId());
		if(existuser!=null&&existuser.getEmailVerification().equals("Y")) {
			UserOp userOp = new UserOp("User Already Exist",1);
			return userOp;
			
		}
		else
		if(existuser!=null&&existuser.getEmailVerification().equals("N")) {
			UserOtp userotp = generateOtp(existuser);
			existuser.setUserOtp(userotp);
			sendRegOtp(existuser, userotp);
			User userSaved = userDao.saveOrUpdate(existuser);
			UserOp userop = new UserOp();
			if (userSaved.getUserId() != 0) {
				userop.setErrFlag(0);
				userop.setMsg("Success");
			} else {
				userop.setErrFlag(0);
				userop.setMsg("Some Error Occured");
			}
			return userop;
			
		}
		return null;
	}
	
	public User saveUserDetails(UserModel user) {
		User newUser = new User();
		newUser.setActiveStatus(1);
		RolesMaster rolesMaster = new RolesMaster();
		if(user.getRole().equalsIgnoreCase("user"))
		rolesMaster.setRoleId(Constants.USER_ROLE);

		if(user.getRole().equalsIgnoreCase("doctor"))
		rolesMaster.setRoleId(Constants.DOCTOR_ROLE);
		
		
		newUser.setRolesMaster(rolesMaster);
		newUser.setUserName(user.getEmailId());
		newUser.setGender(user.getGender());
		newUser.setDoctorRegId(user.getDoctorRegId());
		newUser.setName(user.getName());
		newUser.setMobileNumber(user.getMobileNo());
		if(user.getDob()!=null)
		newUser.setDateOfBirth(Utility.stringToDateWithFormat(user.getDob(),"yyyy-MM-dd"));
		newUser.setEmailVerification("N");
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setCreatedBy("System");
		newUser.setUpdatedBy("System");

		Timestamp curTime = new Timestamp(new Date().getTime());
		newUser.setCreatedTime(curTime);
		newUser.setUpdatedTime(curTime);

		UserOtp userotp = generateOtp(newUser);
		newUser.setUserOtp(userotp);
		sendRegOtp(newUser, userotp);
		return newUser;
		
	}
	
	public UserOtp generateOtp(User newUser) {
		Timestamp curTime = new Timestamp(new Date().getTime());
		
		UserOtp userotp = new UserOtp();
		userotp.setActiveStatus(1);
		userotp.setAttempts(0);
		userotp.setOtpType("Reg");
		userotp.setCreatedBy("System");
		userotp.setCreatedTime(curTime);
		userotp.setOtpNumber((int) Math.round(Math.random() * 100000));

		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
		Date expDate = new Date(timeInSecs + (20 * 60 * 1000));
		Timestamp expTime = new Timestamp(expDate.getTime());

		userotp.setExpiryTime(expTime);
		userotp.setUpdatedBy("System");
		userotp.setUpdatedTime(curTime);
		userotp.setUser(newUser);
		
		return userotp;

	}
	
	public void sendRegOtp(User newUser,UserOtp userotp) {
		EmailDetails ed = new EmailDetails();
		ed.setMsgBody(userotp.getOtpNumber()+"is your verification OTP. It is only valid for next 20 minutes.");
		ed.setRecipient(newUser.getUserName());
		ed.setSubject("Email Verification OTP");
		

        // Try block to check for exceptions
        try {
 
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                = new SimpleMailMessage();
 
            // Setting up necessary details
            mailMessage.setFrom("info@welness.com");
            mailMessage.setTo(newUser.getUserName());
            mailMessage.setText(userotp.getOtpNumber()+"is your verification OTP. It is only valid for next 20 minutes.");
            mailMessage.setSubject("Email Verification OTP");
 
            // Sending the mail
            javaMailSender.send(mailMessage);
            
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<UserModel> findAllUsers() {
		List<User> usersList = userDao.findAll();
		List<UserModel> output = new ArrayList<>();
		for(User user:usersList) {
			UserModel userOp = new UserModel();
			userOp.setEmailId(user.getUserName());
			userOp.setGender(user.getGender());
			userOp.setRole(user.getRolesMaster().getRoleName());
			userOp.setUserId(user.getUserId());
			output.add(userOp);
			
		}
		return output;
	}

	@Override
	public UserOp deleteUser(DeleteUserModel userModel) {
		UserDetails adminDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String cuBy = adminDetails.getUserId()+"";
		Timestamp curTime = new Timestamp(new Date().getTime());
		
		User user = userDao.findById(userModel.getUserId());
		user.setActiveStatus(0);
		user.setUpdatedBy(cuBy);
		user.setUpdatedTime(curTime);
		userDao.saveOrUpdate(user);
		UserOp userOp = new UserOp();
		userOp.setErrFlag(0);
		userOp.setMsg("Successfully Deleted");
		return userOp;
	}

	@Override
	public UserOp editUser(UserModel user) {
		User newUser = new User();
		newUser.setActiveStatus(1);
		RolesMaster rolesMaster = new RolesMaster();
		rolesMaster.setRoleId(Constants.USER_ROLE);
		
		
		newUser.setRolesMaster(rolesMaster);
		newUser.setUserName(user.getEmailId());
		newUser.setGender(user.getGender());
		newUser.setDateOfBirth(Utility.stringToDateWithFormat(user.getDob(),"yyyy-MM-dd"));
		newUser.setEmailVerification("Y");
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		
		UserDetails adminDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String cuBy = adminDetails.getUserId()+"";
		Timestamp curTime = new Timestamp(new Date().getTime());
		
		newUser.setUpdatedBy(cuBy);
		newUser.setUpdatedTime(curTime);

		User userSaved = userDao.saveOrUpdate(newUser);
		UserOp userop = new UserOp();
		if (userSaved.getUserId() != 0) {
			userop.setErrFlag(0);
			userop.setMsg("Success");
		} else {
			userop.setErrFlag(0);
			userop.setMsg("Some Error Occured");
		}
		return userop;

	}

	@Override
	public VerifyOp verifyOTP(VerifyIp verifyIp) {
		User user = userDao.findByUsername(verifyIp.getEmailId());
		UserOtp userOtp = user.getUserOtp();
		VerifyOp verifyOp = new VerifyOp();
		Timestamp curTime = new Timestamp(new Date().getTime());
		if (userOtp.getOtpNumber() == verifyIp.getOtpCode() && userOtp.getExpiryTime().after(curTime)) {
			user.setEmailVerification("Y");
			verifyOp.setErrFlag(0);
			verifyOp.setMsg("Success");
		}
		else if (userOtp.getExpiryTime().after(curTime) && userOtp.getOtpNumber() != verifyIp.getOtpCode()&&userOtp.getAttempts()<3) {
			userOtp.setAttempts(userOtp.getAttempts() + 1);
			verifyOp.setErrFlag(1);
			verifyOp.setMsg("You have "+(3-userOtp.getAttempts())+" Left");
		}
		else if(userOtp.getExpiryTime().before(curTime)||userOtp.getAttempts()>=3) {
			userOtp.setAttempts(userOtp.getAttempts() + 1);
			user.setActiveStatus(0);
			verifyOp.setErrFlag(1);
			verifyOp.setMsg("Please Sign-in Again");
		}
		else {
			verifyOp.setErrFlag(1);
			verifyOp.setMsg("Wrong OTP");
		}
		userDao.saveOrUpdate(user);
		return verifyOp;
	}


	@Override
	public UserOp saveLab(LabModel user) {
		LabDetail labDetail = new LabDetail();
		labDetail.setActiveStatus(1);
		labDetail.setAddress(user.getAddress());
		labDetail.setCreatedBy("System");
		labDetail.setCurrentReceiptNumber(user.getCurrentReceiptNumber());
		labDetail.setLabGstNo(user.getLabGstNo());
		labDetail.setLabNumber(user.getLabNumber());
		labDetail.setLabName(user.getLabName());
		labDetail.setLabRegId(user.getLabRegId());
		labDetail.setTimings(user.getTimings());
		labDetail.setUpdatedBy("System");
		UserModel labAdmin = user.getLabAdmin();
		UserOp validated = validateUser(labAdmin);
		if(validated!=null)
			return validated;
		User newUser = saveUserDetails(labAdmin);
		List<User> users = new ArrayList<>();
		users.add(newUser);
		labDetail.setUsers(users);
		LabDetail lab = userDao.saveLab(labDetail);
		
		UserOp userop = new UserOp();
		if (lab.getLabId() != 0) {
			userop.setErrFlag(0);
			userop.setMsg("Success");
		} else {
			userop.setErrFlag(0);
			userop.setMsg("Some Error Occured");
		}
		return userop;
	}

	@Override
	public UserOp saveDoctor(UserModel user) {
		UserOp validated = validateUser(user);
		if(validated!=null)
			return validated;
		User newUser = saveUserDetails(user);
		/*
						 * List<DoctorDetail> doctorDetail =new ArrayList<>(); for(DoctorHospital
						 * dh:user.getDoctorHospitalList()) { DoctorDetail dd = new DoctorDetail();
						 * dd.setActiveStatus(1); dd.setAddress(dh.getAddress());
						 * dd.setCreatedBy("System"); dd.setUpdatedBy("System");
						 * dd.setCurrentReceiptNumber(dh.getCurrentReceiptNumber());
						 * dd.setHospitalName(dh.getHospitalName());
						 * dd.setHospitalNumber(dh.getHospitalNumber());
						 * dd.setSpecialization(dh.getSpecialization()); dd.setTimings(dh.getTimings());
						 * dd.setUser(newUser); doctorDetail.add(dd); }
						 */
		User userSaved = userDao.save(newUser);
		UserOp userop = new UserOp();
		if (userSaved.getUserId() != 0) {
			userop.setErrFlag(0);
			userop.setMsg("Success");
		} else {
			userop.setErrFlag(0);
			userop.setMsg("Some Error Occured");
		}
		return userop;
	
	}

	@Override
	public TestDataOp getTestData() {
		// TODO Auto-generated method stub
		List<TestMaster> testMasterList = userDao.getTestMaster();
		List<TestDataModel> testDataList = new ArrayList<>();
		for(TestMaster tm:testMasterList) {
			
			List<TestUnitsModel> testUnitList = new ArrayList<TestUnitsModel>();
			for(TestUnitsMaster tum:tm.getTestUnitsMasters()) {
				if(tum.getActiveStatus()==1) {
					TestUnitsModel unitMod = TestUnitsModel.builder()
							.conversionToPrimary(tum.getConversionToPrimary())
							.isPrimary(tum.getIsPrimary())
							.testUnit(tum.getUnitsName())
							.testUnitid(tum.getTestUnitsId())
							.build();
					testUnitList.add(unitMod);
				}
			}
			
			List<TestNamesModel> testNameList = new ArrayList<>();
			for(TestMasterName tmn:tm.getTestMasterNames()) {
				if(tmn.getActiveStatus()==1) {
					TestNamesModel nameMod = TestNamesModel.builder()
							.testName(tmn.getTestName())
							.testNameId(tmn.getTestNamesId())
							.build();
					testNameList.add(nameMod);
				}
			}
			
			
			TestDataModel tdm = TestDataModel.builder()
					.testId(tm.getTestMasterId())
					.testName(tm.getTestName())
					.testCode(tm.getTestCode())
					.testDescription(tm.getTestDescription())
					.testResultType(tm.getTestResultType())
					.testNamesList(testNameList)
					.testUnits(testUnitList)
					.build();
			testDataList.add(tdm);
		}
		TestDataOp tdo=TestDataOp.builder().testDataList(testDataList).build();
		return tdo;
	}

	
}
