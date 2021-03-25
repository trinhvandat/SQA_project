package com.ptit.sqa.controller;

import com.ptit.sqa.model.Mail;
import com.ptit.sqa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class MailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<?> send(@RequestParam("email") String email){
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", "John Michel!");
        properties.put("location", "Sri Lanka");
        properties.put("sign", "Java Developer");

        Mail mail = Mail.builder()
                .from("trinhvandat90399@gmail.com")
                .to(email)
                .htmlTemplate(new Mail.HtmlTemplate("sample", properties))
                .subject("This is sample email with spring boot and thymeleaf")
                .build();
        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
