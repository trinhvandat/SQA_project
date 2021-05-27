package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;

public interface KafkaPublisher {
    /*
    Publish to kafka server
     */
    void sendMessage(CustomerInvoiceDTO customerInvoice);
}
