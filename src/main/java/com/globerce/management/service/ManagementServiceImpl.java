package com.globerce.management.service;


import com.globerce.management.entity.system.ManagementTime;
import com.globerce.management.entity.system.User;
import com.globerce.management.repository.systemRepository.ManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("managementService")
@Repository
@Transactional("systemEntityManager")
public class ManagementServiceImpl implements ManagementService {

    ManagementRepository repository;

    @Autowired
    public void setRepository(ManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<ManagementTime> findAllByUser(User user) {
        return repository.findAllByUserOrderByCurrentDate(user);
    }

    @Override
    public ManagementTime save(ManagementTime managementTime) {
        return repository.save(managementTime);
    }

    @Override
    public Optional<ManagementTime> findById(Long id) {
        return repository.findById(id);
    }

}
