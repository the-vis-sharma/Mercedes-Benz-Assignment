package com.mb.service1.kafka.event;

import com.mb.service1.model.Employee;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddEmployeeEventBody {
    Employee employee;
    String fileType;
}
