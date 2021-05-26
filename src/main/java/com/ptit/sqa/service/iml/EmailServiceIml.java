package com.ptit.sqa.service.iml;

import com.ptit.sqa.dto.response.AddressDTO;
import com.ptit.sqa.dto.response.CustomerInvoiceDTO;
import com.ptit.sqa.model.Mail;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
            InternetAddress internetAddress = new InternetAddress(mail.getTo());
            internetAddress.validate();
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            String html = getHtmlContent(mail);
            helper.addTo(mail.getTo());
            helper.setFrom(EMAIL_FROM);
            helper.setSubject(mail.getSubject());
            helper.setText(html, true);
            emailSender.send(message);
            return true;
        } catch (AddressException e){
            sendMailToAdmin(mail.getTo(), invoice.getCustomer().getName());
            throw new RuntimeException("email address invalid");
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
        mailProperties.put("month", getCurrentMonth());
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

    private String getCurrentMonth(){
        LocalDate dateNow = LocalDate.now();
        return String.format("%s %s %s", dateNow.getMonth().getValue(), "năm", dateNow.getYear());
    }

    private void sendMailToAdmin(String invalidEmail, String customer){
        final String adminEmail = "trinhvandat90399@gmail.com";
        final String errorSendMailSubject = "Send bill to customer fail";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(EMAIL_FROM);
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject(errorSendMailSubject);
        mailMessage.setText(String.format("%s: %s %s: %s. %s", "Send bill to customer", customer,
                "fail, because their email is invalid", invalidEmail,
                "The result of the new stat update has been automatically updated to the original value."));
        emailSender.send(mailMessage);
    }

}
