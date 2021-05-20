package com.ptit.sqa.service.iml;

import com.ptit.sqa.dto.response.AddressDTO;
import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.Mail;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final int unitPrice = 7000;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    private final String WATER_COMPANY_SIGN = "Công ty nước sạch Thủy Long";

    @Override
    public boolean noticePaymentBill(CustomerInvoiceDTO invoice) {
        Mail mail = buildMailEntity(invoice);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            String html = getHtmlContent(mail);

            helper.setTo(mail.getTo());
            helper.setFrom(EMAIL_FROM);
            helper.setSubject(mail.getSubject());
            helper.setText(html, true);

            emailSender.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException("Send email fail", e);
        }
    }

    private Mail buildMailEntity(CustomerInvoiceDTO invoice){

        Mail mail = Mail.builder()
                .to(invoice.getCustomer().getEmail())
                .htmlTemplate(new Mail.HtmlTemplate("sample", buildMailProperties(invoice)))
                .subject("Water bill payment notice")
                .build();
        return mail;
    }
    
    private Map<String, Object> buildMailProperties(CustomerInvoiceDTO invoice){
        Map<String, Object> mailProperties = new HashMap<String, Object>();
        mailProperties.put("name", invoice.getCustomer().getName());
        mailProperties.put("location", invoice.getCustomer().getAddress().getCity());
        mailProperties.put("sign", WATER_COMPANY_SIGN);
        mailProperties.put("oldIndex", invoice.getOldWaterIndexUsed());
        mailProperties.put("newIndex", invoice.getNewWaterIndexUsed());
        mailProperties.put("total", getTotal(invoice.getOldWaterIndexUsed(), invoice.getNewWaterIndexUsed()));
        mailProperties.put("tax", (float)(getTotal(invoice.getOldWaterIndexUsed(), invoice.getNewWaterIndexUsed()) * 0.05));
        mailProperties.put("address", getAddress(invoice.getCustomer().getAddress()));
        mailProperties.put("amount", invoice.getNewWaterIndexUsed() - invoice.getOldWaterIndexUsed());
        mailProperties.put("unitPrice", unitPrice);
        mailProperties.put("totalWithTax", getTotalWithTax(invoice.getOldWaterIndexUsed(), invoice.getNewWaterIndexUsed()));
        return mailProperties;
    }

    private String getAddress(AddressDTO address){
        return String.format(
                "%s, %s, %s, %s",
                address.getNumberHouse(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity()
        );
    }

    private String getHtmlContent(Mail mail) {
        Context context = new Context();
        context.setVariables(mail.getHtmlTemplate().getProps());
        return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
    }

    private float getTotal(int oldIndex, int newIndex){
        float totalWithoutVAT = (float) ((newIndex - oldIndex) * unitPrice);
        return totalWithoutVAT;
    }

    private float getTotalWithTax(int oldIndex, int newIndex){
        float totalWithoutVAT = (float) ((newIndex - oldIndex) * unitPrice);
        return (float) (totalWithoutVAT * 1.05);
    }

}
