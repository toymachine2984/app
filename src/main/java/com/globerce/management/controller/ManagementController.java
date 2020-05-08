package com.globerce.management.controller;

import com.globerce.management.entity.system.ManagementTime;
import com.globerce.management.entity.system.User;
import com.globerce.management.service.ManagementService;
import com.globerce.management.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = "/management")
public class ManagementController {

    private ManagementService managementService;

    private UserService userService;

    @Autowired
    public void setService(ManagementService managementService) {
        this.managementService = managementService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getAll(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication authentication,
                         Model model) {
        Optional<User> userByName = userService.findUserByName(authentication.getName());

        Iterable<ManagementTime> allByUser = managementService.findAllByUser(userByName.get());
        List<ManagementTime> result =
                StreamSupport.stream(allByUser.spliterator(), false)
                        .collect(Collectors.toList());
        if (!result.isEmpty()
                && isToday(new DateTime((result.get(result.size() - 1).getCurrentDate())))) {
            ManagementTime last = result.get(result.size() - 1);
            model.addAttribute("lastDay", last);
            if (last.getStartDate() == null) {
                model.addAttribute("start", true);
            } else if (last.getEndDate() == null) {
                model.addAttribute("end", true);
            }
        } else {
            ManagementTime managementTime = new ManagementTime();
            managementTime.setCurrentDate(new Date());
            managementTime.setUser(userByName.get());
            managementTime = managementService.save(managementTime);
            model.addAttribute("lastDay", managementTime);
            model.addAttribute("start", true);
        }


        model.addAttribute("managementTimes", allByUser);
        return "management";
    }

    @GetMapping(value = "/{id}/start", produces = MediaType.TEXT_HTML_VALUE)
    public String startDay(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable Long id) {
        Optional<ManagementTime> byId = managementService.findById(id);
        ManagementTime managementTime = byId.get();
        managementTime.setStartDate(new Date());
        managementService.save(managementTime);
        return "redirect:/management";
    }

    @GetMapping(value = "/{id}/end", produces = MediaType.TEXT_HTML_VALUE)
    public String endDay(HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable Long id,
                         Authentication authentication) {
        Optional<User> userByName = userService.findUserByName(authentication.getName());
        Optional<ManagementTime> byId = managementService.findById(id);
        if (byId.isPresent()) {
            if (byId.get().getUser().getId().equals(userByName.get().getId())) {
                Date endDate = new Date();
                byId.get().setEndDate(endDate);
                Date workTime = new DateTime(endDate).minus(byId.get().getStartDate().getTime()).minusHours(6).toDate();
                byId.get().setWorkTime(workTime);
                managementService.save(byId.get());
            }
        }
        return "redirect:/management";
    }

    public static boolean isToday(DateTime time) {
        return LocalDate.now().equals(new LocalDate(time));
    }
}
