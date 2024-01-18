package ru.pfr.contracts2.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.entity.user.User_;
import ru.pfr.contracts2.repositories.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll(
                Sort.by(User_.ID).descending()
        );
    }

    public Optional<User> findByLoginUser(String login) {
        return userRepository.findOne(
                Specification.where(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.equal(root.get(User_.LOGIN), login)
                )
        );
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
