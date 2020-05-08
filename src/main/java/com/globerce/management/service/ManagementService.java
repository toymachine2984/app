package com.globerce.management.service;

import com.globerce.management.entity.system.ManagementTime;
import com.globerce.management.entity.system.User;

import java.util.Optional;

public interface ManagementService {

    Iterable<ManagementTime> findAllByUser(User user);

     ManagementTime save(ManagementTime managementTime);

    Optional<ManagementTime> findById(Long id);
}
