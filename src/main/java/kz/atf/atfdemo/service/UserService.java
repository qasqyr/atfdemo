package kz.atf.atfdemo.service;

import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsersByDeleted(Boolean deleted) {
        return userRepository.findAllByDeleted(deleted);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) throws NoSuchElementException {
        return userRepository.findById(userId).get();
    }

    public User deleteUserById(Long userId) throws NoSuchElementException {
        User user = userRepository.findById(userId).get();
        user.setDeleted(true);
        userRepository.save(user);
        return user;
    }

    public Long countAllUsers() {
        return userRepository.count();
    }
}
