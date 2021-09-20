package com.mb.service2.kafka.event;

import lombok.Data;

@Data
public class AddEmployeeEvent {
    Properties properties;
    AddEmployeeEventBody body;
}

