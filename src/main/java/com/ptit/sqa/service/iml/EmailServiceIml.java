package com.ptit.sqa.service.iml;

import com.ptit.sqa.model.Customer;
import com.ptit.sqa.model.Mail;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean noticePaymentBill(Customer customer) {
        Mail mail = buildMailEntity(customer);
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

    private Mail buildMailEntity(Customer customer){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", customer.getName());
        properties.put("location", customer.getAddress().getCity());
        properties.put("sign", WATER_COMPANY_SIGN);

        Mail mail = Mail.builder()
                .to(customer.getEmail())
                .htmlTemplate(new Mail.HtmlTemplate("sample", properties))
                .subject("Water bill payment notice")
                .build();
        return mail;
    }

    private String getHtmlContent(Mail mail) {
        Context context = new Context();
        context.setVariables(mail.getHtmlTemplate().getProps());
        return templateEngine.process(mail.getHtmlTemplate().getTemplate(), context);
    }

}
