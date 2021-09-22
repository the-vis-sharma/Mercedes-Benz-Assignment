package com.mb.service1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mb.service1.kafka.KafkaProducerService;
import com.mb.service1.model.Employee;
import com.mb.service1.util.AES256;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

public class EmployeeServiceTest {

    private static EmployeeService employeeService;
    private static AES256 aes256;
    private static KafkaProducerService kafkaProducerService;
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void setup() {
        aes256 = Mockito.mock(AES256.class);
        kafkaProducerService = Mockito.mock(KafkaProducerService.class);
        restTemplate = Mockito.mock(RestTemplate.class);
        employeeService = new EmployeeService();
        employeeService.aes256 = aes256;
        employeeService.kafkaProducerService = kafkaProducerService;
        employeeService.restTemplate = restTemplate;
        employeeService.processorUrl = "http://localhost:8082/v1/employee/all";

    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Name");
        employee.setAge(20);
        employee.setDob("20-08-2021");
        employee.setSalary(30000.83);
        String fileType = "XML";

        String expectedEvent = "{\"properties\":{\"sender\":\"com.mb.service1\",\"sendDateTime\":\"2021-09-21T11:14:58.839340\",\"messageType\":\"addEmployeeEvent\"},\"body\":{\"employee\":{\"id\":null,\"name\":\"Test Name\",\"dob\":\"20-08-2021\",\"salary\":30000.83,\"age\":20},\"fileType\":\"XML\"}}";

        try {
            employeeService.addEmployee(employee, fileType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Mockito.verify(kafkaProducerService).sendMessage(expectedEvent);

    }

}
