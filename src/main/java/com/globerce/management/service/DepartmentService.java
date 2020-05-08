package com.globerce.management.service;

import com.globerce.management.entity.data.Department;

import java.util.Optional;

public interface DepartmentService {

    Optional<Department> findById(Long id);

    void delete(Long id);

    Department save(Department department);

    Iterable<Department> getAllDepartment();
}
