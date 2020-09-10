package org.vtb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vtb.entity.Role;
import org.vtb.entity.User;
import org.vtb.exception.RegistrationException;
import org.vtb.repository.RoleRepository;
import org.vtb.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userEntityRepository;
    @Autowired
    private RoleRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User userEntity) {
        User user = findByLogin(userEntity.getLogin());
        if(user != null) throw new RegistrationException("Пользователь с такми логином уже существует");
        Role userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRoles(List.of(userRole));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public User findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
