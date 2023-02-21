package ru.pfr.contracts2.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    //@Autowired
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByLoginuser(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
