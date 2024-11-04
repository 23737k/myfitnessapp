package com.myfitnessapp.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@Slf4j
@RequiredArgsConstructor
public class HttpReqScheduledTask {
    private static final int RATE = 840000; //14 minutes
    private final RestTemplate httpClient;
    @Value("${backend.baseUrl}")
    private String baseUrl;
    private String relativeUrl = "/api/rutinas";

    @Scheduled(fixedRate = RATE)
    public void makeHttpCall(){
        httpClient.getForObject(baseUrl + relativeUrl, String.class );
        log.info("Making Http call to avoid render to spin down the server");
    }
}
