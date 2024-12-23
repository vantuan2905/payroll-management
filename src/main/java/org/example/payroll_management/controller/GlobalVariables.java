package org.example.payroll_management.controller;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GlobalVariables {

    @Value("${global.checkIn}")
    private String checkIn;

    @Value("${global.checkOut}")
    private String checkOut;

}