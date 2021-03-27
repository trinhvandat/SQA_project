package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import org.springframework.kafka.annotation.KafkaListener;


public interface KafkaSubscriber {

    @KafkaListener(
            topics = "${kafka.topic.event.mail}",
            containerFactory = "${kafka.consumer.container.factory}"
    )
    void HandlerEventSendEmailNotification(CustomerInvoiceDTO customerInvoice);

}
