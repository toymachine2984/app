package com.globerce.management.controller;

import com.globerce.management.entity.system.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BaseController {


    @GetMapping(value = {"/index", "/"})
    public String index(Model model) {
        return "index";
    }


    @GetMapping(value = {"/login", "/logout"})
    public String login(Model model) {
        return "login";
    }


    @GetMapping(value = {"/cabinet"})
    public String cabinet() {
        return "cabinet";
    }


    @GetMapping(value = {"/companies"}, produces = MediaType.TEXT_HTML_VALUE)
    public String companies() {
        return "companies";
    }

    @GetMapping(value = {"/registration","/user/registration"}, produces = MediaType.TEXT_HTML_VALUE)
    public String registration(Model model){
        model.addAttribute("user",  new User());
        return "registration";
    }

}
