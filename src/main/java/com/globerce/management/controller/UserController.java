package com.globerce.management.controller;


import com.globerce.management.entity.system.EmailVerificationToken;
import com.globerce.management.entity.system.User;
import com.globerce.management.service.MailService;
import com.globerce.management.service.UserService;
import com.globerce.management.util.exceptions.EmailExistsException;
import com.globerce.management.util.registration.OnRegistrationCompleteEvent;
import com.globerce.management.util.viewMessage.ViewMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {


    private UserService userService;
    private ApplicationEventPublisher eventPublisher;
    private BCryptPasswordEncoder passwordEncoder;
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"/registration"}, produces = MediaType.TEXT_HTML_VALUE)
    @Transactional
    public String update(@Valid @ModelAttribute(value = "user") User user,
                         BindingResult bindingResult,
                         Model model,
                         HttpServletRequest request,
                         Locale locale,
                         RedirectAttributes redirectAttributes) {
        User registered = null;
        if (!bindingResult.hasErrors()) {
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setMatchingPassword(encodePassword);
            user.setFirstLogin(false);
            registered = createUserAccount(user);
            if (registered == null) {
                bindingResult.rejectValue("login", "validation.user.create.field.email.exist");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("message",
                    ViewMessage.getMessage("validation.user.create.fail", false, locale));
            model.addAttribute("user", user);
            return "registration";
        } else {
            model.asMap().clear();
            try {
                this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), request.getContextPath()));
            } catch (Exception ex) {
                userService.delete(user);
                ex.printStackTrace();
                model.addAttribute("message",
                        ViewMessage.getMessage("validation.user.create.fail", false, locale));
                model.addAttribute("user", user);
                return "registration";
            }
            redirectAttributes.addFlashAttribute("message",
                    ViewMessage.getMessage("action.token.success", true, locale));
            return "registrationSuccess";
        }
    }

    @GetMapping(value = {"/password"}, produces = MediaType.TEXT_HTML_VALUE)
    public String changePassword(HttpServletRequest request, Model model, Authentication authentication) {
        Optional<User> userByName = userService.findUserByName(authentication.getName());
        model.addAttribute("user", userByName.get());
        return "newPassword";
    }

    @PostMapping(value = {"/password"}, produces = MediaType.TEXT_HTML_VALUE)
    public String changePassword(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @ModelAttribute(value = "user") User user,
                                 Model model) {
        User byId = userService.findById(user.getId());
        String encodePassword = passwordEncoder.encode(user.getPassword());
        byId.setFirstLogin(false);
        byId.setPassword(encodePassword);
        try {
            userService.save(byId);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return "index";
    }


    @GetMapping(value = {"/confirm"}, produces = MediaType.TEXT_HTML_VALUE) // FIXME: 26.09.2018  locale not valid
    public String confirmToken(HttpServletRequest request, Model model,
                               @RequestParam(value = "token") String token,
                               Locale locale) {

        Optional<EmailVerificationToken> optional = userService.findByTokenEquals(token);
        if (optional.isPresent()) {
            EmailVerificationToken verificationToken = optional.get();
            if (verificationToken.getExpiryDate().before(new Date())) {
                model.addAttribute("message", ViewMessage.getMessage("validation.user.create.confirm.expired", false, locale));
                return "redirect:/registrationFailed?lang=" + locale.getLanguage();
            } else {
                User user = verificationToken.getUser();
                user.setExpired(false);
                user.setLocked(false);
                userService.save(user);
                model.addAttribute("message", ViewMessage.getMessage("validation.user.create.confirm", true, locale));
                return "redirect:/index?lang=" + locale.getLanguage();
            }
        }
        model.addAttribute("message", ViewMessage.getMessage("validation.user.create.confirm.empty", false, locale));
        return "redirect:/registrationFailed?lang=" + locale.getLanguage();
    }

    @GetMapping(value = {"/resendToken"})
    @ResponseBody
    public ViewMessage resendVerificationToken(HttpServletRequest request,
                                               @RequestParam(value = "token") String token) {
        Optional<EmailVerificationToken> optional = userService.reissueVerificationToken(token);
        String redirectURL = String.format("%s%s%s", request.getContextPath(), "/user/confirm?token=", token);
        mailService.sendVerificationToken(optional.get().getUser(), redirectURL, request.getLocale());
        return ViewMessage.getMessage("action.token.success", true, request.getLocale());


    }


    private User createUserAccount(User user) {
        try {
            return userService.registerNewUserAccount(user);
        } catch (EmailExistsException e) {
            return null;
        }
    }


}
