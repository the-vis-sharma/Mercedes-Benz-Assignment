package com.mb.service1.kafka.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddEmployeeEvent {
    Properties properties;
    AddEmployeeEventBody body;
}

