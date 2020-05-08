package com.globerce.management.service;

import com.globerce.management.entity.data.Employee;

import java.util.Optional;

public interface EmployeeService {

    Iterable<Employee> getAllEmployee();

    Optional<Employee> findById(Long id);

    void delete(Long id);

    Employee save(Employee employee);
}
