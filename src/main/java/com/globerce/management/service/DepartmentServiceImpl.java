package com.globerce.management.service;

import com.globerce.management.entity.data.Department;
import com.globerce.management.repository.dataRepository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("departmentService")
@Repository
@Transactional("dataEntityManager")
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Optional<Department> findById(Long id){
        return departmentRepository.findById(id);
    }

    public void delete(Long id){
        departmentRepository.deleteById(id);
    }

    public Department save(Department department){
        return departmentRepository.save(department);
    }

    @Override
    public Iterable<Department> getAllDepartment() {
        return this.departmentRepository.findAll();
    }
}
