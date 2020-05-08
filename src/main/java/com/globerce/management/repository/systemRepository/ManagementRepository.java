package com.globerce.management.repository.systemRepository;

import com.globerce.management.entity.system.ManagementTime;
import com.globerce.management.entity.system.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ManagementRepository extends CrudRepository<ManagementTime, Long> {

    @Override
    <S extends ManagementTime> S save(S s);

    @Override
    Optional<ManagementTime> findById(Long aLong);

    @Override
    Iterable<ManagementTime> findAll();

    Iterable<ManagementTime> findAllByUserOrderByCurrentDate(User user);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(ManagementTime time);

}
