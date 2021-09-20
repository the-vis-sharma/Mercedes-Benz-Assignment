package com.mb.service1.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee {

    UUID id;
    String name;
    String dob;
    Double salary;
    Integer age;

}
