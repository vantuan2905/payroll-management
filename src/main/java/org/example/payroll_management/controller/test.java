package org.example.payroll_management.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class test{
    RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/")
    public LocalDate test(){
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL for the API
        String url = "https://open.oapi.vn/date/convert-to-solar"; // Replace with your actual API URL

        // Create the request body as a Map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("day", 10);
        requestBody.put("month", 3);
        requestBody.put("year", 2024);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the request body and headers in an HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        try {
            // Parse JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // Navigate to the "data" node and extract day, month, year
            JsonNode dataNode = rootNode.get("data");
            int day = dataNode.get("day").asInt();
            int month = dataNode.get("month").asInt();
            int year = dataNode.get("year").asInt();
            System.out.println(day + " " + month + " " + year);
            // Create and return LocalDate
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON or extracting date fields", e);
        }
    }
}
