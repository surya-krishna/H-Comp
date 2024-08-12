package com.hope.fit.service;

import java.util.Map;

public interface GoogleFitService {
    Map getStepsCount(String accessToken);

    Map getNutrition(String accessToken);
}
