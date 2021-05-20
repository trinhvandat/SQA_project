package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;


public interface KafkaSubscriber {

    @KafkaListener(
            topics = "${kafka.topic.event.mail}",
            containerFactory = "${kafka.consumer.container.factory}"
    )
    boolean HandlerEventSendEmailNotification(CustomerInvoiceDTO customerInvoice);

    CountDownLatch getLatch();

    String getPayload();

    void setPayload(String payload);
}
