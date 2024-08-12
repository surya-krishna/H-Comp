package com.hope.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hope.root.model.TokenOp;
import com.hope.user.model.ActivityIp;
import com.hope.user.model.AddReportIP;
import com.hope.user.model.ChatIP;
import com.hope.user.model.ConsultationsOp;
import com.hope.user.model.DeleteReportIP;
import com.hope.user.model.DownloadIp;
import com.hope.user.model.GoogleCredentialsIP;
import com.hope.user.model.GeminiIp;
import com.hope.user.model.QueryIp;
import com.hope.user.model.ReportDetailIP;
import com.hope.user.model.ReportDetailsOP;
import com.hope.user.model.ReportListIP;
import com.hope.user.model.ReportListOP;
import com.hope.user.model.ReportStatusOP;
import com.hope.user.model.SharingIp;
import com.hope.user.model.SharingOp;
import com.hope.user.model.TestDetailsIP;
import com.hope.user.model.TestDetailsOP;
import com.hope.user.model.TestListIP;
import com.hope.user.model.TestListOP;
import com.hope.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${loader.host}")
    private String loaderHost;

    

    @PostMapping(value = "addReport")
    public ReportStatusOP addReport(@RequestBody AddReportIP addReportIp) {
        ReportStatusOP addReportOP = userService.addReport(addReportIp);
        return addReportOP;
    }

    @PostMapping(value = "editReport")
    public ReportStatusOP editReport(@RequestBody AddReportIP addReportIp) {
        ReportStatusOP addReportOP = userService.editReport(addReportIp);
        return addReportOP;
    }

    @PostMapping(value = "deleteReport")
    public ReportStatusOP deleteReport(@RequestBody DeleteReportIP deleteReportIp) {
        ReportStatusOP addReportOP = userService.deleteReport(deleteReportIp);
        return addReportOP;
    }

    @PostMapping(value = "getReportDetails")
    public ReportDetailsOP getReportDetails(@RequestBody ReportDetailIP reportDetailIp) {
        ReportDetailsOP reportDetailsOp = userService.getReportDetails(reportDetailIp);
        return reportDetailsOp;
    }

    @PostMapping(value = "getReportList")
    public ReportListOP getReportList(@RequestBody ReportListIP reportListIp) {
        ReportListOP reportListOp = userService.getReportList(reportListIp);
        return reportListOp;
    }

    @PostMapping(value = "getTestDetails")
    public TestDetailsOP getTestDetails(@RequestBody TestDetailsIP testDetailsIp) {
        TestDetailsOP testDetailsOp = userService.getTestDetails(testDetailsIp);
        return testDetailsOp;
    }


    @PostMapping(value = "getTestList")
    public TestListOP getTestList(@RequestBody TestListIP testListIp) {
        TestListOP testListOp = userService.getTestList(testListIp);
        return testListOp;
    }

    @PostMapping(value = "uploadFileCaptureService")
    public Map uploadFileCapture(@RequestBody GeminiIp geminiIp) {
        Map map = userService.uploadFileCapture(geminiIp);
        return map;
    }
    
    @PostMapping(value = "deleteFileCapture")
    public Map deleteFileCapture(@RequestBody GeminiIp geminiIp) {
        Map map = userService.deleteFileCapture(geminiIp);
        return map;
    }
    
    
    

    @PostMapping(value = "extractData")
    public Map extractData(@RequestBody GeminiIp geminiIp) {
        Map map = userService.extractData(geminiIp);
        return map;
    }

    @PostMapping(value = "shareProfile")
    public SharingOp shareProfile(@RequestBody SharingIp sharingIp) {
        SharingOp sharingOp = userService.shareProfile(sharingIp);
        return sharingOp;
    }

    @PostMapping(value = "unShareProfile")
    public SharingOp unShareProfile(@RequestBody SharingIp sharingIp) {
        SharingOp sharingOp = userService.unShareProfile(sharingIp);
        return sharingOp;
    }

    @GetMapping(value = "allConsultations")
    public ConsultationsOp getConsultations() {
        ConsultationsOp sharingOp = userService.getConsultations();
        return sharingOp;
    }

    @GetMapping(value = "activeConsultations")
    public ConsultationsOp getActiveConsultations() {
        ConsultationsOp sharingOp = userService.getActiveConsultations();
        return sharingOp;
    }



    @PostMapping(value = "download")
    public Map downloadFile(@RequestBody DownloadIp downloadIp) {
        return null;
    }

    @PostMapping(value = "downloadContent")
    public Map downloadContent(@RequestBody DownloadIp downloadIp) {
        return null;
    }


    @PostMapping(value = "steps")
    public Map getStepCount(@RequestBody ActivityIp aip) {
        return userService.getStepCount(aip);
    }

    @PostMapping(value = "nutrition")
    public Map getNutrition(@RequestBody ActivityIp aip) {
        return userService.getNutrition(aip);
    }

    @PostMapping(value = "add-google-token")
    public void addGoogleToken(@RequestBody GoogleCredentialsIP credentials) {
        userService.addGoogleCredentials(credentials);
    }
    
    @RequestMapping(value="queryPatientInfo/{data}",method = RequestMethod.GET)
	public SseEmitter queryPatientInfo(@RequestParam String data) {
    	System.out.println(data);
    	QueryIp queryIp = new QueryIp();
    	queryIp.setMessage(data);
    	SseEmitter emitter = new SseEmitter();
        String url = loaderHost+"/chat";

        RequestCallback requestCallback = request -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            List<MediaType> mediaList = new ArrayList<>();
            mediaList.add(MediaType.TEXT_EVENT_STREAM);
            request.getHeaders().setAccept(mediaList);
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ObjectMapper mapper = new ObjectMapper();
            try {
                request.getBody().write(mapper.writeValueAsBytes(queryIp));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        };

        ResponseExtractor<Void> responseExtractor = (ClientHttpResponse response) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    emitter.send(line);
                }
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
            emitter.complete();
            return null;
        };

        new Thread(() -> {
            restTemplate.execute(url, HttpMethod.POST, requestCallback, responseExtractor);
        }).start();

        return emitter;
    }   
    
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public String handleMessage(@Payload String message) {
//        // Forward message to Flask and get response
//        String response = forwardToFlask(message);
//        return response;
//    }

    private String forwardToFlask(String message,int consultationId,int initialMsg,String token) {
    	System.out.println("forwardToFlask");
        String flaskUrl = loaderHost+"/chat_function";//loaderHost+"/chat";
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("message", message);
        requestMap.put("consultationId",consultationId+"");
        requestMap.put("initialMsg",initialMsg+"");
        requestMap.put("token",token);
        
        return restTemplate.postForObject(flaskUrl, requestMap, String.class);
    }
    
    @Autowired
    private SimpMessagingTemplate template;

    
   

    @MessageMapping("/send/message1")
    public void sendMessage1(@Payload ChatIP chatIp,SimpMessageHeaderAccessor headerAccessor){
        System.out.println(chatIp.getMessage());
        if(chatIp.getInitialMsg()==1) {
	        String medicalSummary = userService.getMedicalSummary(chatIp.getConsultationId(),headerAccessor);
	        chatIp.setMessage(medicalSummary+"\n"+chatIp.getMessage());
	        forwardToFlask(medicalSummary,chatIp.getConsultationId(),chatIp.getInitialMsg(),null);
        }        
        String response = forwardToFlask(chatIp.getMessage(),chatIp.getConsultationId(),0,null);    
        
        System.out.println(response);
        this.template.convertAndSend("/message",  response);
    }
    @MessageMapping("/send/message")
    public void sendMessage(@Payload ChatIP chatIp,SimpMessageHeaderAccessor headerAccessor){
        System.out.println(chatIp.getMessage());
        String token = headerAccessor.getFirstNativeHeader("Token");
        if(chatIp.getInitialMsg()==1) {
	        String medicalSummary = userService.getMedicalSummary(chatIp.getConsultationId(),headerAccessor);
	        chatIp.setMessage(medicalSummary+"\n"+chatIp.getMessage());
	        forwardToFlask(medicalSummary,chatIp.getConsultationId(),chatIp.getInitialMsg(),token);
        }        
        String response = forwardToFlask(chatIp.getMessage(),chatIp.getConsultationId(),0,token);    
        
        System.out.println(response);
        this.template.convertAndSend("/message",  response);
    }
    
    @GetMapping("/medicalSummary/{consultation_id}")
    public String medicalSummary(@PathVariable int consultation_id){    
        String medicalSummary = userService.getMedicalSummaryDetails(consultation_id);    
        System.out.println(medicalSummary);
        return medicalSummary;
    }
    
    @GetMapping("/recentTestValues/{consultation_id}")
    public String recentTestValues(@PathVariable int consultation_id){    
        String recentTestValues = userService.getRecentTestValues(consultation_id);  
        System.out.println(recentTestValues);
        return recentTestValues;
    }
}
