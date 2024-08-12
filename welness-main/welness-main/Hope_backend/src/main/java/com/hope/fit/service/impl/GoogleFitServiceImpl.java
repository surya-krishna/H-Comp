package com.hope.fit.service.impl;

import com.hope.fit.service.GoogleFitService;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.fitness.Fitness;
import com.google.api.services.fitness.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.*;
import java.util.*;

@Transactional
@Service
public class GoogleFitServiceImpl implements GoogleFitService {
    private final HttpTransport httpTransport = new NetHttpTransport();
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    @Override
    public Map getStepsCount(String accessToken) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Subtract 7 days from the current date
        LocalDate sevenDaysAgo = currentDate.minusDays(7);

        // Create a LocalDateTime at midnight (00:00:00) of sevenDaysAgo
        LocalDateTime midnightOfSevenDaysAgo = sevenDaysAgo.atTime(LocalTime.MIDNIGHT);

        // Convert to a ZonedDateTime with the desired time zone (e.g., UTC)
        ZonedDateTime zonedDateTime = midnightOfSevenDaysAgo.atZone(ZoneId.of("Asia/Kolkata"));


        Fitness fitness = new Fitness.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("HOPE")
                .build();
        AggregateRequest aggregateRequest = new AggregateRequest();
        aggregateRequest.setAggregateBy(Collections.singletonList(
                new AggregateBy()
                        .setDataSourceId("derived:com.google.step_count.delta:com.google.android.gms:estimated_steps")));
        aggregateRequest.setBucketByTime(new BucketByTime().setDurationMillis(86400000L));
        aggregateRequest.setStartTimeMillis(zonedDateTime.toInstant().toEpochMilli());
        aggregateRequest.setEndTimeMillis(Instant.now().toEpochMilli());

        ArrayList<Integer> steps = new ArrayList<>();

        try {
            AggregateResponse response = fitness.users().dataset().aggregate("me", aggregateRequest).execute();
            for (AggregateBucket aggregateBucket : response.getBucket()) {
                for (Dataset dataset : aggregateBucket.getDataset()) {
                    for (DataPoint dataPoint : dataset.getPoint()) {
                        for (Value value : dataPoint.getValue()) {
                            if (value.getIntVal() != null) {
                                steps.add(value.getIntVal());
                            }
                        }
                    }
                }
            }
            HashMap<String, List<Integer>> map = new HashMap<>();
            map.put("steps", steps);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map getNutrition(String accessToken) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Subtract 7 days from the current date
        LocalDate sevenDaysAgo = currentDate.minusDays(7);

        // Create a LocalDateTime at midnight (00:00:00) of sevenDaysAgo
        LocalDateTime midnightOfSevenDaysAgo = sevenDaysAgo.atTime(LocalTime.MIDNIGHT);

        // Convert to a ZonedDateTime with the desired time zone (e.g., UTC)
        ZonedDateTime zonedDateTime = midnightOfSevenDaysAgo.atZone(ZoneId.of("Asia/Kolkata"));


        Fitness fitness = new Fitness.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("HOPE")
                .build();
        AggregateRequest aggregateRequest = new AggregateRequest();

        aggregateRequest.setAggregateBy(Collections.singletonList(
                new AggregateBy().setDataTypeName("com.google.nutrition")
        ));
        aggregateRequest.setBucketByTime(new BucketByTime().setDurationMillis(86400000L));
        aggregateRequest.setStartTimeMillis(zonedDateTime.toInstant().toEpochMilli());
        aggregateRequest.setEndTimeMillis(Instant.now().toEpochMilli());

        HashMap<String,ArrayList<Double>> nutri = new HashMap<>();

        try {
            AggregateResponse response = fitness.users().dataset().aggregate("me", aggregateRequest).execute();
            for (AggregateBucket aggregateBucket : response.getBucket()) {
                for (Dataset dataset : aggregateBucket.getDataset()) {
                    for (DataPoint dataPoint : dataset.getPoint()) {
                        System.out.println(dataPoint);
                        for (Value value : dataPoint.getValue()) {
                            if (value.getMapVal() != null) {
                                for (ValueMapValEntry valEntry: value.getMapVal()){
                                    if (nutri.containsKey(valEntry.getKey())) {
                                        nutri.get(valEntry.getKey()).add(valEntry.getValue().getFpVal());
                                    } else {
                                        ArrayList<Double> item = new ArrayList<>();
                                        item.add(valEntry.getValue().getFpVal());
                                        nutri.put(valEntry.getKey(), item);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HashMap<String, HashMap<String,ArrayList<Double>>> map = new HashMap<>();
            map.put("nutrition", nutri);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
