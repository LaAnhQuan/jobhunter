package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
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

    public ResultPaginationDTO handleFetchAllUser(Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageUser.getNumber() + 1);
        mt.setPageSize(pageUser.getSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageUser.getContent());
        return rs;
    }

    public User handleGetUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);
        return user;
    }

}
