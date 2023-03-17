package ru.pfr.contracts2.repository.user;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //User findByLogin(String username);
    Optional<User> findByLogin(String login);
}
