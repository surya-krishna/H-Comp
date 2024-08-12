package com.hope.user.service.impl;

import static com.hope.root.model.Constants.TOKEN_PREFIX;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hope.fit.service.GoogleFitService;
import com.hope.root.config.GeminiUtility;
import com.hope.root.config.TokenProvider;
import com.hope.root.config.Utility;
import com.hope.root.entity.GoogleCredentials;
import com.hope.root.entity.Medication;
import com.hope.root.entity.Report;
import com.hope.root.entity.SharingDetail;
import com.hope.root.entity.TestFile;
import com.hope.root.entity.TestMaster;
import com.hope.root.entity.User;
import com.hope.root.entity.UserTest;
import com.hope.root.model.GeminiInput;

import com.hope.root.model.UserDetails;
import com.hope.user.dao.UserDAO;
import com.hope.user.model.ActivityIp;
import com.hope.user.model.AddReportIP;
import com.hope.user.model.ConsultationModel;
import com.hope.user.model.ConsultationsOp;
import com.hope.user.model.DeleteReportIP;
import com.hope.user.model.GoogleCredentialsIP;
import com.hope.user.model.MedicationModel;
import com.hope.user.model.GeminiIp;
import com.hope.user.model.ReportBasicModel;
import com.hope.user.model.ReportDetailIP;
import com.hope.user.model.ReportDetailsOP;
import com.hope.user.model.ReportListIP;
import com.hope.user.model.ReportListOP;
import com.hope.user.model.ReportStatusOP;
import com.hope.user.model.SharingIp;
import com.hope.user.model.SharingOp;
import com.hope.user.model.TestBasicModel;
import com.hope.user.model.TestDetailsIP;
import com.hope.user.model.TestDetailsOP;
import com.hope.user.model.TestFilesModel;
import com.hope.user.model.TestListIP;
import com.hope.user.model.TestListOP;
import com.hope.user.model.TestTimelineModel;
import com.hope.user.model.UserTestsModel;
import com.hope.user.service.UserService;

import ch.qos.logback.classic.pattern.Util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GeminiUtility gemUtil;

    @Autowired
	private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private GoogleFitService fitService;
    
    @Value("${loader.host}")
    private String loaderHost;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;


    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ReportStatusOP addReport(AddReportIP addReportIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timestamp curTime = new Timestamp(new Date().getTime());

        long userId = userDetails.getUserId();
        User user = new User();
        user.setUserId((int) userId);

        try {
            List<UserTest> userTestList = new ArrayList<>();
            List<UserTestsModel> userTestInputList = addReportIp.getUserTestsList();

            Report report = Report.builder()
                    .activeStatus(1)
                    .createdBy(userId + "")
                    .createdTime(curTime)
                    //.age(addReportIp.getAge())
                    //.gender(addReportIp.getGender())
                    //.labPhone(addReportIp.getLabPhone())
                    //.labAddress(addReportIp.getLabAddress())
                    .userTests(new ArrayList<>())
                    //.reportPdfId(addReportIp.getReportPdf())
                    //.labName(addReportIp.getLabName())
                    .containsPII(addReportIp.getContainsPII())
                    //.nameOnReport(addReportIp.getNameOnReport())
                    .reportDate(Utility.stringToDateWithFormat(addReportIp.getReportDate(), "yyyy-MM-dd"))
                    .reportStatus("Approved")
                    .user(user)
                    .updatedBy(userId + "")
                    .updatedTime(curTime).build();

            report.setAge(addReportIp.getAge());
            report.setGender(addReportIp.getGender());
            report.setLabPhone(addReportIp.getLabPhone());
            report.setLabAddress(addReportIp.getLabAddress());
            report.setLabName(addReportIp.getLabName());
            report.setNameOnReport(addReportIp.getNameOnReport());
            for (UserTestsModel utm : userTestInputList) {

                TestMaster tm = null;
			/*if(utm.getTestMasterId()!=null&&utm.getTestMasterId()!=0) {
				tm= new TestMaster();
				tm.setTestMasterId(utm.getTestMasterId());
			}*/


                UserTest userTest = UserTest.builder()
                        .activeStatus(1)
                        .createdBy(userId + "")
                        .createdTime(curTime)
                        .report(report)
                        .testMaster(tm)
                        .units(utm.getUnits())
                        .testMaxNormal(utm.getTestMaxNormal())
                        .testMinNormal(utm.getTestMinNormal())
                        .testName(utm.getTestName())
                        .testResultText(utm.getTestResultText())
                        .observation(utm.getObservation())
                        .suggestions(utm.getSuggestion())
                        .testResultType(utm.getTestResultType())
                        .testValue(utm.getTestValue())
                        .medication((utm.getMedication() != null && utm.getMedication().size() > 0) ? (new ArrayList<>()) : null)
                        .updatedBy(userId + "")
                        .updatedTime(curTime)
                        .user(user).build();

                Medication medication = null;
                for (MedicationModel mm : utm.getMedication()) {
                    medication = Medication.builder()
                            .activeStatus(1)
                            .medication(mm.getMedication())
                            .timings(mm.getTimings())
                            .user(user)
                            .createdBy(userId + "")
                            .createdTime(curTime)
                            .report(report)
                            .updatedBy(userId + "")
                            .updatedTime(curTime)
                            .userTest(userTest).build();
                    userTest.addmedication(medication);
                }

                if ("Files".equalsIgnoreCase(utm.getTestResultType())) {
                    for (TestFilesModel tfm : utm.getFilesList()) {
                        TestFile testFile = TestFile.builder()
                                .activeStatus(1)
                                .createdBy(userId + "")
                                .createdTime(curTime)
                                .testFileAlt(tfm.getTestFileAlt())
                                .testFilePath(tfm.getTestFile())
                                .userTest(userTest)
                                .updatedBy(userId + "")
                                .updatedTime(curTime)
                                .build();
                        userTest.addTestFile(testFile);
                    }
                }
                report.addUserTest(userTest);
            }
            userDAO.saveReport(report);
            report.setReportDownloadUrl(null);
            report.setReportPdfId(null);
            userDAO.saveOrUpdateReport(report);
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(report.getReportId())
                    .errFlag(null)
                    .msg("Success").build();
            return addOp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(null)
                    .errFlag(1)
                    .msg("Failed").build();
            return addOp;
        }


    }


    @Override
    public ReportStatusOP editReport(AddReportIP addReportIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Report existingReport = userDAO.getReportDetails(addReportIp.getReportId(), userDetails.getUserId(),null);
        Timestamp curTime = new Timestamp(new Date().getTime());
        long userId = userDetails.getUserId();
        User user = new User();
        user.setUserId((int) userId);


        try {
            List<UserTestsModel> userTestInputList = addReportIp.getUserTestsList();

            Set<Integer> exTestIdSet = new HashSet<>();
            Set<Integer> exFileIdSet = new HashSet<>();
            Set<Integer> exMedIdSet = new HashSet<>();


            Report report = existingReport;
            report.setAge(addReportIp.getAge());
            report.setGender(addReportIp.getGender());
            report.setLabPhone(addReportIp.getLabPhone());
            report.setLabAddress(addReportIp.getLabAddress());
            report.setReportPdfId(addReportIp.getReportPdf());
            report.setLabName(addReportIp.getLabName());
            report.setNameOnReport(addReportIp.getNameOnReport());
            report.setReportDate(Utility.stringToDateWithFormat(addReportIp.getReportDate(), "yyyy-MM-dd"));
            report.setUpdatedBy(userId + "");
            report.setUpdatedTime(curTime);

            Map<Integer, UserTest> userTestMap = new HashMap<>();
            Map<Integer, TestFile> fileMap = new HashMap<>();
            Map<Integer, Medication> medMap = new HashMap<>();
            for (UserTest ut : report.getUserTests()) {
                if (ut.getActiveStatus() == 1) {
                    userTestMap.put(ut.getUserTestId(), ut);
                    for (TestFile tf : ut.getTestFiles()) {
                        if (tf.getActiveStatus() == 1) {
                            fileMap.put(tf.getTestFileId(), tf);
                        }
                    }
                    for (Medication med : ut.getMedication()) {
                        if (med.getActiveStatus() == 1) {
                            medMap.put(med.getMedicationId(), med);
                        }
                    }
                }

            }

            for (UserTestsModel utm : userTestInputList) {
                if (userTestMap.containsKey(utm.getUserTestId()) || utm.getUserTestId() == null) {

                    TestMaster tm = null;
			/*if(utm.getTestMasterId()!=null&&utm.getTestMasterId()!=0) {
				tm= new TestMaster();
				tm.setTestMasterId(utm.getTestMasterId());
			}*/


                    UserTest userTest = userTestMap.get(utm.getUserTestId());
                    if (userTest == null) {
                        userTest = new UserTest();
                        userTest.setActiveStatus(1);
                        userTest.setCreatedBy(userId + "");
                        userTest.setCreatedTime(curTime);
                        userTest.setUser(user);

                    }
                    if (userTest.getActiveStatus() == 1) {
                        userTest.setReport(report);
                        userTest.setTestMaster(tm);
                        userTest.setUnits(utm.getUnits());
                        userTest.setTestMaxNormal(utm.getTestMaxNormal());
                        userTest.setTestMinNormal(utm.getTestMinNormal());
                        userTest.setTestName(utm.getTestName());
                        userTest.setTestResultText(utm.getTestResultText());
                        userTest.setObservation(utm.getObservation());
                        userTest.setSuggestions(utm.getSuggestion());
                        userTest.setTestResultType(utm.getTestResultType());
                        userTest.setTestValue(utm.getTestValue());
                        userTest.setUpdatedBy(userId + "");
                        userTest.setUpdatedTime(curTime);
                        exTestIdSet.add(utm.getUserTestId());
                        Medication medication = null;
                        for (MedicationModel mm : utm.getMedication()) {
                            if (medMap.containsKey(mm.getMedicationId()) || mm.getMedicationId() == null) {

                                medication = medMap.get(mm.getMedicationId());
                                if (medication == null) {
                                    medication = new Medication();
                                    medication.setActiveStatus(1);
                                    medication.setCreatedBy(userId + "");
                                    medication.setCreatedTime(curTime);
                                    medication.setUser(user);

                                }
                                if (medication.getActiveStatus() == 1) {
                                    medication.setMedication(mm.getMedication());
                                    medication.setTimings(mm.getTimings());
                                    medication.setReport(report);
                                    medication.setUpdatedBy(userId + "");
                                    medication.setUpdatedTime(curTime);
                                    medication.setUserTest(userTest);
                                    userTest.addmedication(medication);
                                    exMedIdSet.add(mm.getMedicationId());
                                }
                            }
                        }

                        if ("Files".equalsIgnoreCase(utm.getTestResultType())) {
                            for (TestFilesModel tfm : utm.getFilesList()) {
                                if (fileMap.containsKey(tfm.getTestFileId()) || tfm.getTestFileId() == null) {

                                    TestFile testFile = fileMap.get(tfm.getTestFileId());
                                    if (testFile == null) {
                                        testFile = new TestFile();
                                        testFile.setActiveStatus(1);
                                        testFile.setCreatedBy(userId + "");
                                        testFile.setCreatedTime(curTime);

                                    }
                                    if (testFile.getActiveStatus() == 1) {
                                        testFile.setTestFileAlt(tfm.getTestFileAlt());
                                        testFile.setTestFilePath(tfm.getTestFile());
                                        testFile.setUserTest(userTest);
                                        testFile.setUpdatedBy(userId + "");
                                        testFile.setUpdatedTime(curTime);
                                        userTest.addTestFile(testFile);
                                        exFileIdSet.add(tfm.getTestFileId());
                                    }
                                }
                            }
                        }

                        report.addUserTest(userTest);
                    }
                }

            }

            Set<Integer> userTestSet = userTestMap.keySet();
            Set<Integer> fileSet = fileMap.keySet();
            Set<Integer> medSet = medMap.keySet();
            userTestSet.removeAll(exMedIdSet);
            fileSet.remove(exFileIdSet);
            medSet.removeAll(exMedIdSet);
            for (Integer i : userTestSet) {
                userTestMap.get(i).setActiveStatus(1);
            }
            for (Integer i : fileSet) {
                fileMap.get(i).setActiveStatus(1);
            }
            for (Integer i : medSet) {
                medMap.get(i).setActiveStatus(1);
            }


            userDAO.saveOrUpdateReport(report);
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(report.getReportId())
                    .errFlag(null)
                    .msg("Success").build();
            return addOp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(null)
                    .errFlag(1)
                    .msg("Failed").build();
            return addOp;
        }


    }

	
	
	/*@Override
	public UploadFileOp uploadFile(MultipartFile uploadFileIp) {
		MultipartFile file = uploadFileIp;
		UploadFileOp uop = new UploadFileOp();
		if (file.isEmpty()) {
            uop.setErrFlg(1);
            return uop;
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //System.out.println(file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            String[] fileParts =fileName.split("\\.");
            int len =fileParts.length;
            //System.out.println(fileParts[0]);
            String extension = fileParts[len-1];
            String pathName = UPLOADED_FOLDER + DatePlusUUID()+"."+extension;
            pathName = pathName.replaceAll(" ", "");
            Path path = Paths.get(pathName);
            Files.write(path, bytes);
            uop.setFilePath(pathName);
            return uop;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}*/


    @Override
    public ReportStatusOP deleteReport(DeleteReportIP deleteReportIp) {
        // TODO Auto-generated method stub
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Report existingReport = userDAO.getReportDetails(deleteReportIp.getReportId(), userDetails.getUserId(),null);
            Timestamp curTime = new Timestamp(new Date().getTime());
            long userId = userDetails.getUserId();
            User user = new User();
            user.setUserId((int) userId);
            existingReport.setActiveStatus(0);
            existingReport.setUpdatedBy(userId + "");
            existingReport.setUpdatedTime(curTime);
            for (UserTest userTest : existingReport.getUserTests()) {
                userTest.setActiveStatus(0);
                userTest.setUpdatedBy(userId + "");
                userTest.setUpdatedTime(curTime);
                for (Medication med : userTest.getMedication()) {
                    med.setActiveStatus(0);
                    med.setUpdatedBy(userId + "");
                    med.setUpdatedTime(curTime);
                }
                for (TestFile tf : userTest.getTestFiles()) {
                    tf.setActiveStatus(0);
                    tf.setUpdatedBy(userId + "");
                    tf.setUpdatedTime(curTime);
                }
            }

            userDAO.saveOrUpdateReport(existingReport);
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(existingReport.getReportId())
                    .errFlag(null)
                    .msg("Success").build();
            return addOp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ReportStatusOP addOp = ReportStatusOP.builder()
                    .reportId(null)
                    .errFlag(1)
                    .msg("Failed").build();
            return addOp;
        }

    }


    public int getUserIdFromSharing(int sharingId) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails.getRole().equalsIgnoreCase("role_user"))
            return (int) userDetails.getUserId();
        System.out.println("Sharing id================="+sharingId);
        SharingDetail sd = userDAO.getSharingDetail(sharingId, userDetails.getUserId());
        return sd.getUser().getUserId();
        }else {
        	logger.info("Auth is null");
        }
        return -1;
    }
    
    
    public void getUserDetailsWebSocket(SimpMessageHeaderAccessor accessor) {
    	System.out.println("===============authToken****************");
    	
        
        String token = accessor.getFirstNativeHeader("Token");
        
        String username=null;
        if (token != null) {
            
            System.out.println("===============authToken"+token);
            try {
            	token = token.replace(TOKEN_PREFIX,"");
                username = jwtTokenUtil.getUsernameFromToken(token);
                System.out.println("===============authToken ====="+ username);
            } catch (IllegalArgumentException e) {
            	System.out.println("an error occured during getting username from token"+ e);
            } catch (ExpiredJwtException e) {
            	System.out.println("the token is expired and not valid anymore"+ e);
            } catch(SignatureException e){
            	System.out.println("Authentication Failed. Username or Password not valid.");
            }
        } else {
        	System.out.println("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	 org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
            	
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                System.out.println("authenticated user " + username + ", setting security context"+authentication);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
            }
        }

    }

    
    @Override
    public ReportDetailsOP getReportDetails(ReportDetailIP reportDetailIp) {
        // TODO Auto-generated method stub
    	System.out.println("date:"+reportDetailIp.getDate());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(reportDetailIp.getConsultationId());
        try {
            Report report = userDAO.getReportDetails(reportDetailIp.getReportId(), userId,reportDetailIp.getDate());
            if (report != null) {
                List<UserTestsModel> userTestList = new ArrayList<UserTestsModel>();
                for (UserTest ut : report.getUserTests()) {
                    if (ut.getActiveStatus() == 1) {
                        List<TestFilesModel> testFileList = new ArrayList<TestFilesModel>();
                        for (TestFile tf : ut.getTestFiles()) {
                            if (tf.getActiveStatus() == 1) {
                                TestFilesModel tfm = TestFilesModel.builder()
                                        .testFile(tf.getTestFilePath())
                                        .testFileAlt(tf.getTestFileAlt())
                                        .testFileId(tf.getTestFileId())
                                        .build();
                                testFileList.add(tfm);
                            }
                        }
                        List<MedicationModel> testMedList = new ArrayList<MedicationModel>();
                        for (Medication med : ut.getMedication()) {
                            if (med.getActiveStatus() == 1) {
                                MedicationModel medMod = MedicationModel.builder()
                                        .medicationId(med.getMedicationId())
                                        .medication(med.getMedication())
                                        .timings(med.getTimings())
                                        .build();
                                testMedList.add(medMod);
                            }
                        }

                        UserTestsModel utm = UserTestsModel.builder()
                                .filesList(testFileList)
                                .medication(testMedList)
                                .observation(ut.getObservation())
                                .suggestion(ut.getSuggestions())
                                .testMaxNormal(ut.getTestMaxNormal())
                                .testMinNormal(ut.getTestMinNormal())
                                .testName(ut.getTestName())
                                .testResultText(ut.getTestResultText())
                                .testResultType(ut.getTestResultType())
                                .testValue(ut.getTestValue())
                                .units(ut.getUnits())
                                .userTestId(ut.getUserTestId())
                                .build();
                        userTestList.add(utm);
                    }
                }
                ReportDetailsOP detailsOp = ReportDetailsOP.builder()
                        .reportId(report.getReportId())
                        .labName(report.getLabName())
                        .age(report.getAge())
                        .gender(report.getGender())
                        .labAddress(report.getLabAddress())
                        .containsPII(report.getContainsPII())
                        .labId(report.getLabDetail() != null ? report.getLabDetail().getLabId() : null)
                        .nameOnReport(report.getNameOnReport())
                        .reportDate(Utility.dateToStringWithFormat(report.getReportDate(), "yyyy-MM-dd"))
                        .userTestsList(userTestList)
                        .build();
                return detailsOp;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ReportDetailsOP detailsOp = ReportDetailsOP.builder()
                    .reportId(null)
                    .errFlag(1)
                    .msg("Failed").build();
            return detailsOp;
        }
        return null;

    }


    @Override
    public ReportListOP getReportList(ReportListIP reportListIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(reportListIp.getConsultationId());
        try {
            List<Report> reports = userDAO.getReports(reportListIp, userId);
            List<ReportBasicModel> reportBasicModelList = new ArrayList<ReportBasicModel>();

            for (Report report : reports) {
                List<String> testList = new ArrayList<String>();
                List<UserTest> utList = report.getUserTests();
                for (UserTest ut : utList) {
                    if (ut.getActiveStatus() == 1) {
                        testList.add(ut.getTestName());
                    }
                }
                ReportBasicModel rbm = ReportBasicModel.builder()
                        .reportId(report.getReportId())
                        .labName(report.getLabName())
                        .nameOnReport(report.getNameOnReport())
                        .reportDate(Utility.dateToStringWithFormat(report.getReportDate(), "yyyy-MM-dd"))
                        .testsList(testList)
                        .build();
                reportBasicModelList.add(rbm);
            }
            ReportListOP listOp = ReportListOP.builder().reportBasicModelList(reportBasicModelList).build();
            return listOp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ReportListOP listOp = ReportListOP.builder()
                    .errFlag(1)
                    .msg("Failed").build();
            return listOp;
        }

    }


    @Override
    public TestDetailsOP getTestDetails(TestDetailsIP testDetailsIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(testDetailsIp.getConsultationId());
        try {
            List<UserTest> userTests = userDAO.getTestDetails(testDetailsIp.getTestName(), userId);
            List<TestTimelineModel> userTestsList = new ArrayList<TestTimelineModel>();
            for (UserTest ut : userTests) {
                List<TestFilesModel> testFileList = new ArrayList<TestFilesModel>();
                for (TestFile tf : ut.getTestFiles()) {
                    if (tf.getActiveStatus() == 1) {
                        TestFilesModel tfm = TestFilesModel.builder()
                                .testFile(tf.getTestFilePath())
                                .testFileAlt(tf.getTestFileAlt())
                                .testFileId(tf.getTestFileId())
                                .build();
                        testFileList.add(tfm);
                    }
                }
                List<MedicationModel> testMedList = new ArrayList<MedicationModel>();
                for (Medication med : ut.getMedication()) {
                    if (med.getActiveStatus() == 1) {
                        MedicationModel medMod = MedicationModel.builder()
                                .medicationId(med.getMedicationId())
                                .medication(med.getMedication())
                                .timings(med.getTimings())
                                .build();
                        testMedList.add(medMod);
                    }
                }


                Report report = ut.getReport();

                TestTimelineModel ttm = TestTimelineModel.builder()
                        .labName(report.getLabName())
                        .medication(testMedList)
                        .observation(ut.getObservation())
                        .suggestion(ut.getSuggestions())
                        .testDate(Utility.dateToStringWithFormat(report.getReportDate(), "yyyy-MM-dd"))
                        .testMaxNormal(ut.getTestMaxNormal())
                        .testMinNormal(ut.getTestMinNormal())
                        .testName(ut.getTestName())
                        .testResultText(ut.getTestResultText())
                        .testResultType(ut.getTestResultType())
                        .testValue(ut.getTestValue())
                        .filesList(testFileList)
                        .build();
                userTestsList.add(ttm);
            }
            Collections.sort(userTestsList);
            TestDetailsOP tdop = TestDetailsOP.builder().userTestsList(userTestsList).build();
            return tdop;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TestDetailsOP tdop = TestDetailsOP.builder()
                    .errFlag(1)
                    .msg("Failed").build();
            return tdop;
        }
    }

    @Override
    public TestListOP getTestList(TestListIP testListIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(testListIp.getConsultationId());
        try {
            //count(userTestId),testName,testResultType,avg(testValue),avg(testMaxNormal),avg(testMinNormal),test_master_id
            List<Object[]> userTests = userDAO.getTestList(testListIp, userId);
            List<TestBasicModel> testBasicList = new ArrayList<TestBasicModel>();
            for (Object[] test : userTests) {
                TestBasicModel tbm = TestBasicModel.builder()
                        .count((Long) (test[0] != null ? test[0] : 0))
                        .meanNormalMax((Double) (test[4] != null ? test[4] : 0) + "")
                        .meanNormalMin((Double) (test[5] != null ? test[5] : 0) + "")
                        .meanValue((test[3] != null ? test[3] : 0) + "")
                        .testName(test[1].toString())
                        .testResultType(test[2].toString())
                        .build();
                testBasicList.add(tbm);
            }
            TestListOP tlp = TestListOP.builder().testBasicList(testBasicList).build();
            return tlp;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            TestListOP tlp = TestListOP.builder()
                    .errFlag(1)
                    .msg("Failed").build();
            return tlp;
        }

    }


    @Override
    public Map uploadFileCapture(GeminiIp geminiIp) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeminiInput gemIp = new GeminiInput();
        gemIp.setReqestString(geminiIp.getRequestString());
        gemIp.setUrl(loaderHost+"/upload_file");
        gemIp.setMethod("post");
        return gemUtil.callAPI(gemIp);

    }



    @Override
    public Map extractData(GeminiIp geminiIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeminiInput gemIp = new GeminiInput();
        gemIp.setReqestString(geminiIp.getRequestString());
        gemIp.setUrl(loaderHost+"/extract_data");
        gemIp.setMethod("post");
        return gemUtil.callAPI(gemIp);

    }



    @Override
   
    public SharingOp shareProfile(SharingIp sharingIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User d = userDAO.getDoctor(sharingIp);
        if (d == null) {
            SharingOp sop = new SharingOp();
            sop.setActiveFlag(1);
            sop.setErrFlag(0);
            return sop;
        }
        Timestamp curTime = new Timestamp(new Date().getTime());
        long userId = userDetails.getUserId();
        SharingDetail sd = new SharingDetail();
        sd.setActiveStatus(1);
        sd.setAccessedOn(null);
        sd.setDoctor(d);
        User user = new User();
        user.setUserId((int) userId);
        sd.setUser(user);
        sd.setCreatedBy(userId + "");
        sd.setCreatedTime(curTime);
        sd.setUpdatedBy(userId + "");
        sd.setUpdatedTime(curTime);
        int i = new Random().nextInt(900000) + 100000;
        sd.setOtpNumber(i);
        sd.setExpiryTime(0);
        userDAO.saveSharingDetails(sd);
        SharingOp sop = new SharingOp();
        sop.setActiveFlag(1);
        sop.setErrFlag(0);
        return sop;

    }


    @Override
    public ConsultationsOp getConsultations() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = userDetails.getUserId();
        List<SharingDetail> sharing = userDAO.getConsultations("All", userId);
        List<ConsultationModel> consultations = new ArrayList<>();
        for (SharingDetail share : sharing) {
            ConsultationModel cm = new ConsultationModel();
            cm.setConsultationDate(Utility.dateToStringWithFormat(share.getCreatedTime(), "yyyy-MM-dd"));
            cm.setConsultationId(share.getSharingId());
            cm.setUserName(share.getDoctor().getUserName());
            cm.setOtp(share.getOtpNumber() + "");
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
        List<SharingDetail> sharing = userDAO.getConsultations("Active", userId);
        List<ConsultationModel> consultations = new ArrayList<>();
        for (SharingDetail share : sharing) {
            ConsultationModel cm = new ConsultationModel();
            cm.setConsultationDate(Utility.dateToStringWithFormat(share.getCreatedTime(), "yyyy-MM-dd"));
            cm.setConsultationId(share.getSharingId());
            cm.setUserName(share.getDoctor().getUserName());
            cm.setOtp(share.getOtpNumber() + "");
            consultations.add(cm);
        }
        ConsultationsOp cop = new ConsultationsOp();
        cop.setConsultationList(consultations);
        return cop;

    }


    @Override
    public SharingOp unShareProfile(SharingIp sharingIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Timestamp curTime = new Timestamp(new Date().getTime());
        long userId = userDetails.getUserId();
        SharingDetail sd = userDAO.getUserSharingDetail(sharingIp.getSharingId(), userId);
        sd.setActiveStatus(0);
        sd.setUpdatedBy(userId + "");
        sd.setUpdatedTime(curTime);
        userDAO.updateSharingDetails(sd);
        SharingOp sop = new SharingOp();
        sop.setActiveFlag(1);
        sop.setErrFlag(0);
        return sop;
    }



    @Override
    public Map getStepCount(ActivityIp aip) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(aip.getConsultationId()); 
        GoogleCredentials credentials = userDAO.getGoogleCredentials(userId);
        return fitService.getStepsCount(credentials.getAccessToken());
    }

    @Override
    public Map getNutrition(ActivityIp aip) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = getUserIdFromSharing(aip.getConsultationId()); 
        GoogleCredentials credentials = userDAO.getGoogleCredentials(userId);
        return fitService.getNutrition(credentials.getAccessToken());
    }

    @Override
    public void addGoogleCredentials(GoogleCredentialsIP credentials) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setUserId((int) userDetails.getUserId());
        GoogleCredentials obj = GoogleCredentials.builder()
                .accessToken(credentials.getAccessToken())
                .tokenType(credentials.getTokenType())
                .expiresIn(credentials.getExpiresIn())
                .scope(credentials.getScope())
                .user(user).build();
        userDAO.addGoogleCredentials(obj);
    }

    @Override
    public Map deleteFileCapture(GeminiIp geminiIp) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeminiInput gemIp = new GeminiInput();
        gemIp.setReqestString(geminiIp.getRequestString());
        String[] fileNameParts=geminiIp.getRequestString().split("/");
        String fileName = fileNameParts[fileNameParts.length-1];
        if(fileName!=null&&!fileName.equalsIgnoreCase("files")) {
        gemIp.setUrl(loaderHost+"/delete_file?filename="+fileName);
        gemIp.setMethod("delete");
        return gemUtil.callAPI(gemIp);
        }
        return null;
    }


	@Override
	public String getMedicalSummary(int consultationId,SimpMessageHeaderAccessor headerAccessor) {
		getUserDetailsWebSocket(headerAccessor);
		long userId = getUserIdFromSharing(consultationId);
		List<Object[]> response= userDAO.getMedicalSummary(userId);
		StringBuffer sb = new StringBuffer();
		int k=0;
		for(Object[] obj:response) {
			if(k>0) {
				sb.append(",");
			}
			String st = "{"
					//+ "\"testName\":\""+obj[1]+"\","
					//+ "\"averageTestValue\":"+obj[3]+","
					//+ "\"testMaxNormalValue\":"+obj[4]+","
					//+ "\"testMinNormalValue\":"+obj[5]+","
					//+ "\"variance\":"+obj[6]+","
					+ "\"observation\":\""+obj[7]+"\","
					+ "\"doctor_suggestion\":\""+obj[8]+"\""
					//+ "\"NumberOfTestsTaken\":"+obj[0]+""
					+ "}";
					
			sb.append(st);
			k++;
		}
		List<Object[]> lastTests =userDAO.getRecentTests(userId); 
		StringBuffer recent = new StringBuffer();
		k=0;
		for(Object[] obj:lastTests) {
			if(k>0) {
				recent.append(",");
			}
			String st = "{"
					+ "\"testName\":\""+obj[0]+"\","
					+ "\"testValue\":"+obj[1]+","
					+ "\"testMaxNormalValue\":"+obj[3]+","
					+ "\"testMinNormalValue\":"+obj[4]+","
					+ "\"observation\":\""+obj[5]+"\","
					+ "\"doctor_suggestion\":\""+obj[6]+"\","
					+ "\"testsTakenDate\":"+obj[7]+""
					+ "}";
		    System.out.println(obj[7].toString());
		    System.out.println((Date)obj[7]);
		    System.out.println(Utility.dateToStringWithFormat((Date)obj[7],"yyyy-MM-dd"));
			recent.append(st);
			k++;
		}
		
		//String result = "You are an healthcare assistant, and you will be asked questions by a healthcare professional to better understand patient condition. while reponding keep it as short as possible, you answer questions related to health condition of the user with following health vitals."
		//		+ "Most recent values of each test taken is in below json object \n ["+recent.toString()+"]"+
		//		"\n\n Consolidated report of each test taken is in below json object \n ["+sb.toString()+"]";
		String result = "";
		if(consultationId!=0) {
			result = "You are an healthcare assistant, and you will be asked questions by a healthcare professional to better understand patient condition. you answer questions related to health condition of the user. and summarize the responses to human readable form.always deal date with dd-mm-yyyy (like 21-10-2024) format only.";
		}
		else {
			result = "You are an healthcare companion, and you will be asked questions by a user to better understand their medical condition. respond with questions related to domain of healthcare like diet to be followed, excercise to do and such relevant topics . and summarize the responses to human readable form.always deal date with dd-mm-yyyy (like 21-10-2024) format only. when asked for suggestions like diet, exercise etc, Always give them based on medicalSummary Suggestions Observation";
					//+ " in below json object \n ["+sb.toString()+"]"; 
		}
		System.out.println(result);
		return result;
	}
	
	
	@Override
	public String getMedicalSummaryDetails(int consultationId) {
		long userId = getUserIdFromSharing(consultationId);
		List<Object[]> response= userDAO.getMedicalSummary(userId);
		StringBuffer sb = new StringBuffer();
		int k=0;
		for(Object[] obj:response) {
			if(k>0) {
				sb.append(",");
			}
			String st = "{"
					+ "\"testName\":\""+obj[1]+"\","
					+ "\"averageTestValue\":"+obj[3]+","
					+ "\"testMaxNormalValue\":"+obj[4]+","
					+ "\"testMinNormalValue\":"+obj[5]+","
					+ "\"variance\":"+obj[6]+","
					+ "\"observation\":\""+obj[7]+"\","
					+ "\"doctor_suggestion\":\""+obj[8]+"\","
					+ "\"NumberOfTestsTaken\":"+obj[0]+""
					+ "}";
					
			sb.append(st);
			k++;
		}
			
		return "["+sb.toString()+"]";
	}
	
	@Override
	public String getRecentTestValues(int consultationId) {
		long userId = getUserIdFromSharing(consultationId);
		List<Object[]> lastTests =userDAO.getRecentTests(userId); 
		StringBuffer recent = new StringBuffer();
		int k=0;
		for(Object[] obj:lastTests) {
			if(k>0) {
				recent.append(",");
			}
			String st = "{"
					+ "\"testName\":\""+obj[0]+"\","
					+ "\"testValue\":"+obj[1]+","
					+ "\"testMaxNormalValue\":"+obj[3]+","
					+ "\"testMinNormalValue\":"+obj[4]+","
					+ "\"observation\":\""+obj[5]+"\","
					+ "\"doctor_suggestion\":\""+obj[6]+"\","
					+ "\"testsTakenDate\":\""+obj[7]+"\""
					+ "}";
		    System.out.println(obj[7].toString());
		    System.out.println((Date)obj[7]);
		    System.out.println(Utility.dateToStringWithFormat((Date)obj[7],"yyyy-MM-dd"));
			recent.append(st);
			k++;
		}
		
		System.out.println(recent.toString());
		return "["+recent.toString()+"]";
	}




}
