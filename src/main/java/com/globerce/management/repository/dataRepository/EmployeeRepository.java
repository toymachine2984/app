package com.globerce.management.repository.dataRepository;

import com.globerce.management.entity.data.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Override
    Optional<Employee> findById(Long aLong);

    @Override
    Iterable<Employee> findAll();

    void deleteById(Long id);

    @Override
    <S extends Employee> S save(S s);
}
