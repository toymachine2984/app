package com.globerce.management.repository.systemRepository;


import com.globerce.management.entity.system.EmailVerificationToken;
import com.globerce.management.entity.system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationTokenRepository extends CrudRepository<EmailVerificationToken, Long> {

    @Override
    <S extends EmailVerificationToken> S save(S entity);

    Optional<EmailVerificationToken> findByTokenEquals(String token);

    Optional<EmailVerificationToken> findByUser(User user);


}
