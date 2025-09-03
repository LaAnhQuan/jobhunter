package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResCreUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUpUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreUserDTO handleCreateUser(User user) {
        User newUser = this.userRepository.save(user);
        return UserMapper.toResCreUserDTO(newUser);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id không tồn tại: " + id));
        this.userRepository.deleteById(id);
    }

    public ResUserDTO handleFetchUserById(long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id không tồn tại: " + id));
        return UserMapper.toResUserDTO(user);
    }

    public ResUpUserDTO handleUpdateUser(User user) {
        User existingUser = this.userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user với id = " + user.getId()));

        existingUser.setName(user.getName());
        existingUser.setGender(user.getGender());
        existingUser.setAge(user.getAge());
        existingUser.setAddress(user.getAddress());

        userRepository.save(existingUser);
        return UserMapper.toResUpUserDTO(existingUser);
    }

    public ResultPaginationDTO handleFetchAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);

        // Convert User -> ResUserDTO
        List<ResUserDTO> userDTOs = pageUser.getContent()
                .stream()
                .map(UserMapper::toResUserDTO)
                .collect(Collectors.toList());

        // Meta info
        Meta mt = new Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        // Response wrapper
        ResultPaginationDTO rs = new ResultPaginationDTO();
        rs.setMeta(mt);
        rs.setResult(userDTOs);
        return rs;
    }

    public User handleGetUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username);
    }
}
