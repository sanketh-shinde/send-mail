package com.eidiko.email.controller;

import com.eidiko.email.dto.UserEntityDTO;
import com.eidiko.email.entity.MailEntity;
import com.eidiko.email.entity.UserEntity;
import com.eidiko.email.exception.UserNotFoundException;
import com.eidiko.email.service.MailEntityService;
import com.eidiko.email.service.SendMailService;
import com.eidiko.email.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/user")
@SessionAttributes(names = {"loggedInUser", "otp", "toUpdate", "message"})
public class UserController {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private MailEntityService mailEntityService;

    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "register";
    }

    @PostMapping("/process-registration")
    public String register(UserEntity userEntity) {
        UserEntityDTO registeredUserEntityDTO = userEntityService.createUser(userEntity);
        if (registeredUserEntityDTO != null) {
            return "redirect:/user/login";
        }
        return "redirect:/user/register";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/process-login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) throws UserNotFoundException {
        UserEntityDTO loginedUserEntityDTO = userEntityService.getUser(email, password);
        if (loginedUserEntityDTO != null) {
            model.addAttribute("loggedInUser", loginedUserEntityDTO);
            return "redirect:/user/home";
        }
        model.addAttribute("message", "Invalid Credentials");
        return "redirect:/user/login";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/process-forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        UserEntityDTO userByMail = userEntityService.getUserByMail(email);
        model.addAttribute("toUpdate", userByMail);
        MailEntity mailType = mailEntityService.getMailType("forgot-password");
        if (userByMail != null) {
            Random random = new Random();
            int tempOtp = 1000 + random.nextInt(9000);
            String otp = String.valueOf(tempOtp);
            model.addAttribute("otp", otp);
            sendMailService.sendMailWithHtml(userByMail.getEmail(), mailType.getSubject(), mailType.getBody(), userByMail.getName(), otp);
            return "redirect:/user/change-password";
        }
        model.addAttribute("message", "mail does not exists");
        return "redirect:/user/forgot-password";
    }

    @GetMapping("/change-password")
    public String getChangePasswordPage(Model model) {
        return "change-password";
    }

    @PostMapping("/process-change-password")
    public String updatePassword(@RequestParam String otp, @RequestParam String password, @RequestParam String confirmPassword, Model model) throws UserNotFoundException {
        UserEntityDTO userEntityDTO = (UserEntityDTO) model.getAttribute("toUpdate");

        String generatedOtp = (String) model.getAttribute("otp");
        if (userEntityDTO != null) {
            if (generatedOtp.equals(otp)) {
                if (password.equals(confirmPassword)) {
                    UserEntityDTO updatedUserEntityDTO = userEntityService.updatePassword(password, userEntityDTO.getEmail());
                    if (updatedUserEntityDTO != null) {
                        model.addAttribute("message", "Password Updated");
                        return "redirect:/user/login";
                    } else {
                        model.addAttribute("message", "Password Update Failed, Try again later!");
                        return "redirect:/user/change-password";
                    }
                } else {
                    model.addAttribute("message", "Password does not match");
                    return "redirect:/user/change-password";
                }
            } else {
                model.addAttribute("message", "Wrong OTP");
                return "redirect:/user/change-password";
            }
        }
        model.addAttribute("message", "mail does not exists");
        return "redirect:/user/forgot-password";
    }

    @PostMapping("/send-mail")
    public String sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String body, Model model) {
        UserEntityDTO loggedUser = (UserEntityDTO) model.getAttribute("loggedInUser");
        if (loggedUser != null) {
            sendMailService.sendMail(loggedUser.getEmail(), to, subject, body);
            model.addAttribute("message", "something went wrong, try again later");
        }
        model.addAttribute("message", "mail sent successfully");
        return "redirect:/user/home";
    }

}
