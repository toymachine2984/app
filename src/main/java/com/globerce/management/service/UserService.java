package com.globerce.management.service;



import com.globerce.management.entity.system.EmailVerificationToken;
import com.globerce.management.entity.system.User;
import com.globerce.management.util.exceptions.EmailExistsException;

import java.util.Optional;

public interface UserService {


    <S extends User> S save(S s);


    User findById(Long aLong);


    Iterable<User> findAll();


    void deleteById(Long aLong);


    void delete(User user);

    User findUserByRevision(Long id, int revision);

    Optional<User> findUserByName(String name);

    User registerNewUserAccount(User accountDto) throws EmailExistsException;

    boolean isLoginExist(String email);

    <S extends EmailVerificationToken> S save(S entity);

    EmailVerificationToken createVerificationToken(User user, String token);

    Optional<EmailVerificationToken> findByTokenEquals(String token);

    Optional<EmailVerificationToken> reissueVerificationToken(String token);
}
