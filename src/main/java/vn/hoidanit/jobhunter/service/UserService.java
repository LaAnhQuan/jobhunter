package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

    }

    public User handleCreateUser(User user) {
        User newUser = user;
        return this.userRepository.save(newUser);
    }

    public void handleDeleteUser(long id) {

        this.userRepository.deleteById(id);
    }

    public User handleFetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public Optional<User> handleUpdateUser(User user) {
        return this.userRepository.findById(user.getId()).map(user1 -> {
            user1.setName(user.getName());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            return userRepository.save(user1);
        });
    }

    public List<User> handleFetchAllUser() {
        return this.userRepository.findAll();
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
