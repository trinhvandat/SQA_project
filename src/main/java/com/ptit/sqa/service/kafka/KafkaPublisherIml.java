package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.CustomerInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPublisherIml implements KafkaPublisher{

    private final KafkaTemplate<String, CustomerInvoiceDTO> kafkaTemplate;

    @Value("${kafka.topic.event.mail}")
    private String orderPublisherTopic;

    @Override
    public void sendMessage(CustomerInvoiceDTO customerInvoice) {
        kafkaTemplate.send(orderPublisherTopic, "test" ,customerInvoice);
        log.info("send message to order publisher completed: {}", customerInvoice.getCustomer().getName());
    }
}
