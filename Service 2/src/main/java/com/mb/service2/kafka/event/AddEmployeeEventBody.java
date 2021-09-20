package com.mb.service2.kafka.event;

import com.mb.service2.model.Employee;
import lombok.Data;

@Data
public class AddEmployeeEventBody {
    Employee employee;
    String fileType;
}
