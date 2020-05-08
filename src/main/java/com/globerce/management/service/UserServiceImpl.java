package com.globerce.management.service;

import com.globerce.management.entity.system.EmailVerificationToken;
import com.globerce.management.entity.system.Role;
import com.globerce.management.entity.system.User;
import com.globerce.management.repository.systemRepository.RoleRepository;
import com.globerce.management.repository.systemRepository.UserRepository;
import com.globerce.management.repository.systemRepository.VerificationTokenRepository;
import com.globerce.management.util.exceptions.EmailExistsException;
import com.globerce.management.util.exceptions.UserIdNotFoundException;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;


@Service("userService")
@Repository
@Transactional("systemEntityManager")
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private VerificationTokenRepository verificationTokenRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }


    @Override
    @Transactional(readOnly = true)
    public User findById(Long aLong) {
        return userRepository.findById(aLong).orElseThrow(() -> new UserIdNotFoundException(aLong));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByRevision(Long id, int revision) {
        return AuditReaderFactory.get(entityManager).find(User.class, id, revision);
    }

    @Override
    public Optional<User> findUserByName(String name){
        return userRepository.findByLoginEquals(name);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byLoginEquals = userRepository.findByLoginEquals(username);
        return byLoginEquals.map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                .accountLocked(user.isLocked())
                .accountExpired(user.isExpired())
                .build()).orElseThrow(() -> new UsernameNotFoundException(String.format("User with name: %s not found.", username)));
    }


    @Override
    @Transactional
    public User registerNewUserAccount(User user) throws EmailExistsException {
        if (!isLoginExist(user.getLogin())) {
            user.getRoles().add(new Role(2L, "ROLE_USER"));
            return userRepository.save(user);
        } else {
            throw new EmailExistsException(user.getLogin());
        }
    }

    @Transactional
    @Override
    public boolean isLoginExist(String login) {
        return userRepository.findByLoginEquals(login).isPresent();
    }

    @Transactional
    @Override
    public EmailVerificationToken createVerificationToken(User user, String token) {
        return verificationTokenRepository.save(new EmailVerificationToken(token, user));
    }


    @Transactional
    @Override
    public Optional<EmailVerificationToken> reissueVerificationToken(String token) {
        Optional<EmailVerificationToken> byTokenEquals = verificationTokenRepository.findByTokenEquals(token);
        String newToken = UUID.randomUUID().toString();
        byTokenEquals.ifPresent(o -> o = new EmailVerificationToken(newToken));
        return byTokenEquals;
    }

    @Override
    public <S extends EmailVerificationToken> S save(S entity) {
        return verificationTokenRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<EmailVerificationToken> findByTokenEquals(String token) {
        Optional<EmailVerificationToken> byTokenEquals = verificationTokenRepository.findByTokenEquals(token);
        byTokenEquals.ifPresent(vt -> {
            User currentUser = vt.getUser();
            currentUser.setRoles(roleRepository.findAllByUsers(currentUser));
        });
        return byTokenEquals;
    }
}
