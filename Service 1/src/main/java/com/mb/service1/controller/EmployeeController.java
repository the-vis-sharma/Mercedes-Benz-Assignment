package com.mb.service1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mb.service1.model.Employee;
import com.mb.service1.model.Response;
import com.mb.service1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/add")
    ResponseEntity<Response> addEmployee(@RequestParam String fileType, @RequestBody Employee employee) throws JsonProcessingException {
        employeeService.addEmployee(employee, fileType);
        Response response = new Response();
        response.setSuccess(true);
        response.setPayload("Employee Details added successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<Response> updateEmployee(@RequestParam String fileType, @RequestBody Employee employee) throws JsonProcessingException {
        employeeService.updateEmployee(employee, fileType);
        Response response = new Response();
        response.setSuccess(true);
        response.setPayload("Employee Details updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    ResponseEntity<String> getAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK);
    }




}
