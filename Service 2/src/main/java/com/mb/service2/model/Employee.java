package com.mb.service2.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@Data
@XmlRootElement(name = "employee")
@XmlType(propOrder = { "id", "name", "dob", "salary", "age" })
public class Employee {

    UUID id;
    String name;
    String dob;
    Double salary;
    Integer age;

}
