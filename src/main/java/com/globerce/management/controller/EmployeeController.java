package com.globerce.management.controller;

import com.globerce.management.entity.data.Department;
import com.globerce.management.entity.data.Employee;
import com.globerce.management.entity.system.Role;
import com.globerce.management.entity.system.User;
import com.globerce.management.repository.systemRepository.RoleRepository;
import com.globerce.management.service.DepartmentService;
import com.globerce.management.service.EmployeeService;
import com.globerce.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    private UserService userService;

    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    private DepartmentService departmentService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getAll(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model) {
        Iterable<Employee> employees = employeeService.getAllEmployee();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(value = {"/{id}"}, produces = MediaType.TEXT_HTML_VALUE)
    public String getEmployee(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable("id") long id,
                              Model model) {
        Optional<Employee> employee = employeeService.findById(id);
        model.addAttribute("employee", employee.get());
        return "employee";
    }

    @PostMapping(value = {"/admin/{id}/update"}, produces = MediaType.TEXT_HTML_VALUE)
    public String updateEmployee(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @ModelAttribute("employeeForm") Employee employee,
                                 Model model) {
        Employee save = employeeService.save(employee);
        model.addAttribute("employee", save);
        return "employee";
    }

    @GetMapping(value = {"/admin/{id}/delete"}, produces = MediaType.TEXT_HTML_VALUE)
    public String deleteEmployee(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @PathVariable("id") long id,
                                 Model model) {
        Employee employee = employeeService.findById(id).get();
        Optional<User> userByName = userService.findUserByName(employee.getEmail());
        userByName.ifPresent(user -> userService.delete(user));
        employeeService.delete(id);
        Iterable<Employee> employees = employeeService.getAllEmployee();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping(value = {"/admin/add"}, produces = MediaType.TEXT_HTML_VALUE)
    public String addEmployee(HttpServletRequest request,
                              HttpServletResponse response,
                              @Valid @ModelAttribute(value = "employee") Employee employee,
                              Model model) {
        User user;
        if(employee.getId() == null){
            user = new User();
            user.setId(employee.getId());
            Optional<Role> byId = roleRepository.findById(2L);
            user.setExpired(false);
            Set<Role> roles = new HashSet<>();
            roles.add(byId.get());
            user.setLocked(false);
            user.setPassword(passwordEncoder.encode("Topse8610"));
            user.setMatchingPassword(user.getPassword());
            user.setFirstLogin(true);
            user.setRoles(roles);
        } else {
            Optional<Employee> byId = employeeService.findById(employee.getId());
            user = userService.findUserByName(byId.get().getEmail()).get();
        }
        user.setLogin(employee.getEmail());
        user.setFirstName(employee.getFirstName());
        user.setLastName(employee.getLastName());
        Employee save = employeeService.save(employee);
        try {
            userService.save(user);
        } catch (Exception e) {
            e.getMessage();
        }
        model.addAttribute("employee", save);
        return "employee";
    }

    @GetMapping(value = {"/admin/add", "/admin/{id}/update"}, produces = MediaType.TEXT_HTML_VALUE)
    public String newEmployeeForm(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable(value = "id", required = false) Long id,
                                  Model model) {
        Employee employee;
        if (id != null) {
            Optional<Employee> byId = employeeService.findById(id);
            employee = byId.get();
        } else {
            employee = new Employee();
        }
        Iterable<Department> allDepartment = departmentService.getAllDepartment();
        model.addAttribute("departments", allDepartment);
        model.addAttribute("employee", employee);
        return "newEmployee";
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
