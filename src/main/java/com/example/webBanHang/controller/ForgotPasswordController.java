package com.example.webBanHang.controller;

import com.example.webBanHang.configuration.Utility;
import com.example.webBanHang.model.User;
import com.example.webBanHang.service.customer.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomStringUtils.randomAlphabetic(30);

        try {
            userService.updateResetPasswordToken(token, email);

            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token="  + token;

            sendEmail(email, resetPasswordLink);
        } catch
        (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email.");
        }

        return "forgot_password_form";
    }

    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("youremail@gmail.com");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p> Hello, </p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password: </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\"> Change my password </a><b></p>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request </p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "login";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "login";
    }
}
