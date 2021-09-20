package com.mb.service2.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Properties {

    String sender;
    String sendDateTime;
    String messageType;

}
