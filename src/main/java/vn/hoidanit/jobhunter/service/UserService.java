package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResCreUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUpUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

public interface UserService {

    ResCreUserDTO handleCreateUser(User user);

    void handleDeleteUser(long id);

    ResUserDTO handleFetchUserById(long id);

    ResUpUserDTO handleUpdateUser(User user);

    ResultPaginationDTO handleFetchAllUser(Specification<User> spec, Pageable pageable);

    User handleGetUserByUsername(String username) throws UsernameNotFoundException;

    boolean isEmailExist(String email);

    void updateUserToken(String token, String email);
}
