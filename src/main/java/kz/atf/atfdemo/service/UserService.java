package kz.atf.atfdemo.service;

import kz.atf.atfdemo.model.User;
import kz.atf.atfdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId).get();
    }

    public User deleteUserById(Long userId) throws Exception {
        User user = userRepository.findById(userId).get();
        user.setDeleted(true);
        userRepository.save(user);
        return user;
    }
}
