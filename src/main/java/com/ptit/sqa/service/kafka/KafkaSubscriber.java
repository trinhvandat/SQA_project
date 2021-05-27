package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.concurrent.CountDownLatch;


public interface KafkaSubscriber {

    /*
    auto listener from kafka topic
    topic: your config
    container factory: your config
    Handler message and send email to customer
     */
    @KafkaListener(
            topics = "${kafka.topic.event.mail}",
            containerFactory = "${kafka.consumer.container.factory}"
    )
    boolean HandlerEventSendEmailNotification(CustomerInvoiceDTO customerInvoice, Acknowledgment ack);

    CountDownLatch getLatch();

    String getPayload();

    void setPayload(String payload);
}
