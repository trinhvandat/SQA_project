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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceIml implements EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    private final String WATER_COMPANY_SIGN = "Water company";

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
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", invoice.getCustomer().getName());
        properties.put("location", invoice.getCustomer().getAddress().getCity());
        properties.put("sign", WATER_COMPANY_SIGN);
        properties.put("oldIndex", invoice.getOldWaterIndexUsed());
        properties.put("newIndex", invoice.getNewWaterIndexUsed());
        properties.put("total", getTotal(invoice.getOldWaterIndexUsed(), invoice.getNewWaterIndexUsed()));
        properties.put("address", getAddress(invoice.getCustomer().getAddress()));

        Mail mail = Mail.builder()
                .to(invoice.getCustomer().getEmail())
                .htmlTemplate(new Mail.HtmlTemplate("sample", properties))
                .subject("Water bill payment notice")
                .build();
        return mail;
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
        float totalWithoutVAT = (float) ((newIndex - oldIndex) * 7000);
        return (float) (totalWithoutVAT * 0.9);
    }

}
