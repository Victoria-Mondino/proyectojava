package com.finaldejavaMondino.demo.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DateService {

    private final RestTemplate restTemplate;

    public Date obtenerFecha() {
        try {
            ResponseEntity<WorldClockResponse> response = restTemplate.getForEntity(
                    "http://worldclockapi.com/api/json/utc/now",
                    WorldClockResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String fechaStr = response.getBody().getCurrentDateTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                return format.parse(fechaStr);
            }
        } catch (Exception e) {
            // Loggear error
        }
        return new Date();
    }

    @Data
    private static class WorldClockResponse {
        @JsonProperty("currentDateTime")
        private String currentDateTime;

        // Debe coincidir exactamente con el nombre del campo JSON
        public String getCurrentDateTime() {
            return currentDateTime;
        }
    }
}