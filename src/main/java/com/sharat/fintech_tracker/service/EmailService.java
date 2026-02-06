package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.model.Expense;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ByteArrayResource;

import jakarta.mail.MessagingException;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendExpenseReportEmail(String to, List<Expense> expenses, ByteArrayResource pdfResource)
            throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("your-email@gmail.com");
        helper.setTo(to);
        helper.setSubject("Expense Report");
        helper.setText("Please find your expense report attached.");

        // Attach PDF report
        helper.addAttachment("expense-report.pdf", pdfResource);

        emailSender.send(mimeMessage);
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
