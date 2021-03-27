package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;

public interface KafkaPublisher {
    void sendMessage(CustomerInvoiceDTO customerInvoice);
}
