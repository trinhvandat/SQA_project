package com.ptit.sqa.service.kafka;

import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.repository.CustomerInvoiceRepository;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class KafkaSubscriberIml implements KafkaSubscriber{

    private final EmailService emailService;
    private final CustomerInvoiceRepository customerInvoiceRepository;

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    /*
    implement
     */
    @Override
    public boolean HandlerEventSendEmailNotification(@Payload CustomerInvoiceDTO invoice, Acknowledgment ack) {
        log.info("receive");
        ack.acknowledge(); // notice the kafka server if ack record
        try {
            boolean notificationResult = emailService.noticePaymentBill(invoice);
            log.info("Result send water bill notification to customer: {} is: {}", invoice.getCustomer().getName(), notificationResult);
            setPayload(invoice.toString());
            latch.countDown();
            return notificationResult;
        } catch (Exception e){
            /*
            Call back data in database. reUpdate record invoices in database
             */
            customerInvoiceRepository.findById(invoice.getId())
                    .ifPresent(customerInvoice -> {
                        customerInvoice.setNewWaterIndexUsed(null);
                        customerInvoiceRepository.save(customerInvoice);
                    });
            throw new RuntimeException("Send Email to customer fail");
        }
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
