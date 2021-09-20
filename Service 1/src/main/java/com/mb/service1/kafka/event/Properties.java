package com.mb.service1.kafka.event;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Properties {

    String sender;
    String sendDateTime;
    String messageType;

}
