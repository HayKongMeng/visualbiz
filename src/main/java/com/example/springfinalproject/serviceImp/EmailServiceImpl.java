package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.model.request.ProductWithQtyRequest;
import com.example.springfinalproject.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String optCode, String email, String subject) throws MessagingException {
        Context context = new Context();
        context.setVariable("optCode", optCode);
        String process = templateEngine.process("index", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(process, true);
        ClassPathResource image = new ClassPathResource("images/ac2660ca-b9eb-4799-8638-869c6fe606be.png");
        mimeMessageHelper.addInline("logo", image);
        mimeMessageHelper.setTo(email);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendInvoice(String profileImg, String username, LocalDateTime dateTime, Integer orderId, String shopName, String shopAddress, String email, List<ProductWithQtyRequest> products) throws MessagingException {
        Context context = new Context();
        context.setVariable("profileImg", profileImg);
        context.setVariable("username", username);
        context.setVariable("dateTime", dateTime);
        context.setVariable("orderId", orderId);
        context.setVariable("shopName", shopName);
        context.setVariable("shopAddress", shopAddress);
        context.setVariable("products", products);
        String process = templateEngine.process("invoice", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("Invoice You have been order");
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setTo(email);
        javaMailSender.send(mimeMessage);
    }
}
