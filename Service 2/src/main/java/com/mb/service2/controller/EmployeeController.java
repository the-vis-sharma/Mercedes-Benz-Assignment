package com.mb.service2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.service2.model.Employee;
import com.mb.service2.model.Response;
import com.mb.service2.service.EmployeeService;
import com.mb.service2.util.AES256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "v1/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AES256 aes256;

    @GetMapping("/all")
    ResponseEntity<String> getAllEmployee() throws JAXBException, IOException {
        List<Employee> employeeList = employeeService.getAllEmployee();
        Response response = new Response();
        response.setSuccess(true);
        response.setPayload(employeeList);

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = mapper.writeValueAsString(response);
        return new ResponseEntity<>(aes256.encrypt(responseJson), HttpStatus.OK);
    }

}
