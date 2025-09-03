package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

public interface UserService {

    User handleCreateUser(User user);

    void handleDeleteUser(long id);

    User handleFetchUserById(long id);

    Optional<User> handleUpdateUser(User user);

    ResultPaginationDTO handleFetchAllUser(Specification<User> spec, Pageable pageable);

    User handleGetUserByUsername(String username) throws UsernameNotFoundException;

}
