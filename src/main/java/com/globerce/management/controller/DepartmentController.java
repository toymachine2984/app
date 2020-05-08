package com.globerce.management.controller;

import com.globerce.management.entity.data.Department;
import com.globerce.management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = {"/admin/add"}, produces = MediaType.TEXT_HTML_VALUE)
    public String addDepartment(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable(value = "id", required = false) Long id,
                                  Model model) {
        Department department;
        if (id != null) {
            Optional<Department> byId = departmentService.findById(id);
            department = byId.get();
        } else {
            department = new Department();
        }
        model.addAttribute("department", department);
        return "newDepartment";
    }

    @PostMapping(value = {"/admin/add"}, produces = MediaType.TEXT_HTML_VALUE)
    public String addDepartment(HttpServletRequest request,
                                HttpServletResponse response,
                                @Valid @ModelAttribute(value = "department") Department department,
                                Model model) {
        departmentService.save(department);
        Iterable<Department> allDepartment = departmentService.getAllDepartment();
        model.addAttribute("departments", allDepartment);
        return "departments";
    }

    @GetMapping(value = {"/admin/{id}/delete"}, produces = MediaType.TEXT_HTML_VALUE)
    public String deleteDepartment(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @PathVariable("id") long id,
                                   Model model) {
        departmentService.delete(id);
        Iterable<Department> departments = departmentService.getAllDepartment();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getAllDepartments(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Model model) {
        Iterable<Department> allDepartment = departmentService.getAllDepartment();
        model.addAttribute("departments", allDepartment);
        return "departments";
    }
}
