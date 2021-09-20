package com.mb.service2.kafka.event;

import lombok.Data;

@Data
public class UpdateEmployeeEvent {
    Properties properties;
    UpdateEmployeeEventBody body;
}

