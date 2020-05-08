package com.globerce.management.service;

import com.globerce.management.entity.data.Employee;
import com.globerce.management.repository.dataRepository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("employeeService")
@Repository
@Transactional("dataEntityManager")
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> findById(Long id){
        return employeeRepository.findById(id);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    @Override
    public Iterable<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }
}
