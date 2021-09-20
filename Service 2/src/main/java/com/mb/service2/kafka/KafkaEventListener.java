package com.mb.service2.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.service2.kafka.event.AddEmployeeEvent;
import com.mb.service2.kafka.event.UpdateEmployeeEvent;
import com.mb.service2.service.AddEmployeeProcessor;
import com.mb.service2.service.UpdateEmployeeProcessor;
import com.mb.service2.util.AES256;
import com.mb.service2.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class KafkaEventListener {

    @Autowired
    AddEmployeeProcessor addEmployeeProcessor;

    @Autowired
    UpdateEmployeeProcessor updateEmployeeProcessor;

    @Autowired
    AES256 aes256;

    @KafkaListener(topics = "employee", groupId = "employee_processor_group")
    public void handleMessage(String message) throws IOException, JAXBException {
        log.info("Received Message: " + message);
        message = aes256.decrypt(message);
        log.info("De-crypted message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> messageMap = mapper.readValue(message, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> properties = (Map<String, Object>) messageMap.get("properties");
        String messageType = (String) properties.get("messageType");

        switch (messageType) {
            case Constants.ADD_EMPLOYEE_EVENT:
                AddEmployeeEvent addEmployeeEvent = mapper.readValue(message, AddEmployeeEvent.class);
                addEmployeeProcessor.process(addEmployeeEvent);
                break;

            case Constants.UPDATE_EMPLOYEE_EVENT:
                UpdateEmployeeEvent updateEmployeeEvent = mapper.readValue(message, UpdateEmployeeEvent.class);
                updateEmployeeProcessor.process(updateEmployeeEvent);
                break;

            default:
                log.error("Unknown event received: " + messageType);
        }
    }

}
