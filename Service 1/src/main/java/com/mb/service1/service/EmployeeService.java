package com.mb.service1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.service1.kafka.KafkaProducerService;
import com.mb.service1.kafka.event.*;
import com.mb.service1.model.Employee;
import com.mb.service1.util.AES256;
import com.mb.service1.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class EmployeeService {

    @Value("${processor.url}")
    String processorUrl;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AES256 aes256;

    public void addEmployee(Employee employee, String fileType) throws JsonProcessingException {
        Properties properties = new Properties();
        properties.setSender(Constants.APP_NAME);
        properties.setMessageType(Constants.ADD_EMPLOYEE_EVENT);
        properties.setSendDateTime(LocalDateTime.now().toString());

        AddEmployeeEventBody body = new AddEmployeeEventBody();
        body.setEmployee(employee);
        body.setFileType(fileType);

        AddEmployeeEvent event = new AddEmployeeEvent();
        event.setProperties(properties);
        event.setBody(body);

        ObjectMapper mapper = new ObjectMapper();
        kafkaProducerService.sendMessage(mapper.writeValueAsString(event));
    }

    public void updateEmployee(Employee employee, String fileType) throws JsonProcessingException {
        Properties properties = new Properties();
        properties.setSender(Constants.APP_NAME);
        properties.setMessageType(Constants.UPDATE_EMPLOYEE_EVENT);
        properties.setSendDateTime(LocalDateTime.now().toString());

        UpdateEmployeeEventBody body = new UpdateEmployeeEventBody();
        body.setEmployee(employee);
        body.setFileType(fileType);

        UpdateEmployeeEvent event = new UpdateEmployeeEvent();
        event.setProperties(properties);
        event.setBody(body);

        ObjectMapper mapper = new ObjectMapper();
        kafkaProducerService.sendMessage(mapper.writeValueAsString(event));
    }

    public String getAllEmployee() {
        ResponseEntity<String> response = restTemplate.getForEntity(processorUrl, String.class);
        String responseJson = aes256.decrypt(response.getBody());
        return responseJson;
    }

}
