package org.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vtb.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query(value = "SELECT * FROM users as u inner join users_roles as ur on u.id = ur.user_id\n" +
            "inner join roles as r on ur.role_id = r.id where r.name in ?1",
            nativeQuery = true)
    List<User> findAllWithRoles(List<String> roles);
}
