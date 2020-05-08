package com.globerce.management.repository.dataRepository;

import com.globerce.management.entity.data.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Override
    Optional<Department> findById(Long aLong);

    @Override
    Iterable<Department> findAll();

    void deleteById(Long id);

    @Override
    <S extends Department> S save(S s);
}
