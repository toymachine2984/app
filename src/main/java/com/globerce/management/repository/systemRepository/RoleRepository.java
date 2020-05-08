package com.globerce.management.repository.systemRepository;


import com.globerce.management.entity.system.Role;
import com.globerce.management.entity.system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.Set;


@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Override
    <S extends Role> S save(S s);

    @Override
    Optional<Role> findById(Long aLong);

    @Override
    Iterable<Role> findAll();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Role role);

    Set<Role> findAllByUsers(User user);
}
