package com.hope.root.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hope.root.model.GeminiInput;
import com.hope.user.service.impl.UserServiceImpl;
import com.google.api.client.googleapis.media.MediaHttpUploader;

@Component
public class GeminiUtility {


    @Value("${gemini.apiKey}")
    private String apiKey;
    
    @Autowired
    RestTemplate rest;

    Logger logger = LoggerFactory.getLogger(GeminiUtility.class);

    public Map<String, Object> callAPI(GeminiInput geminiIp) {
        String url = geminiIp.getUrl();
        try {
            //RestTemplate rest = null;
            String requestStr = geminiIp.getReqestString();
            System.out.println(requestStr);
            String authToken = null;
            logger.info(authToken);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.set("Authorization", "Bearer " + authToken);
           //logger.info("Bearer " + authToken);
            HttpEntity<String> request = null;
            Map<String, Object> result = null;
            if (geminiIp.getMethod().equalsIgnoreCase("post")) {

                request = new HttpEntity<String>(requestStr, headers);
                System.out.println("inside post");
                result = rest.postForObject(url, request, Map.class);
                for(Entry<String,Object> entry:result.entrySet()) {
                	System.out.println(entry.getKey()+" "+entry.getValue());
                }
            } else if (geminiIp.getMethod().equalsIgnoreCase("get")) {
                request = new HttpEntity<String>(headers);
                result = rest.exchange(url, HttpMethod.GET, request, Map.class).getBody();
            } else if (geminiIp.getMethod().equalsIgnoreCase("delete")) {
                request = new HttpEntity<String>(headers);
                logger.info(url);
                result = rest.exchange(url, HttpMethod.DELETE, request, Map.class).getBody();
            } else if (geminiIp.getMethod().equalsIgnoreCase("getFile")) {
                request = new HttpEntity<String>(headers);
                ResponseEntity<byte[]> resp = rest.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        byte[].class
                );
                if (resp.getStatusCode() == HttpStatus.OK) {
                    byte[] bytes = resp.getBody();
                    Map<String, Object> respMap = new HashMap<>();
                    respMap.put("fileBytes", bytes);
                    return respMap;
                } else {
                    return null;
                }

            }
            else
            if(geminiIp.getMethod().equalsIgnoreCase("getJs")) {
            	headers.set("Accept", "application/javascript;charset=utf-8");
            	request = new HttpEntity<String>(headers);
                String obj = rest.exchange(url, HttpMethod.GET, request, String.class).getBody();
                System.out.println(obj);
                result = new HashMap<>();
                result.put("JS",obj);
            }
            return result;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

 }
