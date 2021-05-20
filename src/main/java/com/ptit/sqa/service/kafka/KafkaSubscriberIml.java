package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class KafkaSubscriberIml implements KafkaSubscriber{

    private final EmailService emailService;

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @Override
    public boolean HandlerEventSendEmailNotification(CustomerInvoiceDTO customerInvoice) {
        log.info("receive");
        boolean notificationResult = emailService.noticePaymentBill(customerInvoice);
        log.info("Result send water bill notification to customer: {} is: {}", customerInvoice.getCustomer().getName(), notificationResult);
        setPayload(customerInvoice.toString());
        latch.countDown();
        return notificationResult;
    }

    @Override
    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
