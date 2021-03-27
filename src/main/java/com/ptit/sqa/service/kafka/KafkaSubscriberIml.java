package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class KafkaSubscriberIml implements KafkaSubscriber{

    private final EmailService emailService;

    @Override
    public void HandlerEventSendEmailNotification(CustomerInvoiceDTO customerInvoice) {
        log.info("receive");
        boolean notificationResult = emailService.noticePaymentBill(customerInvoice);
        log.info("Result send water bill notification to customer: {} is: {}", customerInvoice.getCustomer().getName(), notificationResult);
    }
}
