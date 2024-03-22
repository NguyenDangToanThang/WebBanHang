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
        return "login/forgot_password_form";
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

        return "login/forgot_password_form";
    }

    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("youremail@gmail.com");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p> Xin chào, </p>"
                + "<p>Bạn có 1 yêu cầu làm mới mật khẩu.</p>"
                + "<p>Ấn vào đường dẫn bên dưới để thay đổi mật khẩu của bạn </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\"> Đổi mật khẩu </a><b></p>"
                + "<p>Hãy bỏ qua Email này nếu bạn đã nhớ ra mật khẩu của bạn hoặc không phải bạn </p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Token không chính xác");
            return "login/reset_password_form";
        }

        return "login/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Làm mới mật khẩu của bạn");

        if (user == null) {
            model.addAttribute("message", "Token không chính xác");
            return "login/reset_password_form";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("message", "Bạn đã đổi mật khẩu thành công");
        }

        return "login/login";
    }
}
