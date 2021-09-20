package com.mb.service1.kafka;

import com.mb.service1.util.AES256;
import com.mb.service1.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    AES256 aes256;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("Producing Message: " + message);

        message = aes256.encrypt(message);
        log.info("encrypted message: " + message);

        kafkaTemplate.send(Constants.KAFKA_TOPIC_FILE, message)
                .addCallback(sendResult -> log.info("Message send successfully."),
                        e -> log.error("Message failed to send with error: " + e.getMessage()));
    }

}
