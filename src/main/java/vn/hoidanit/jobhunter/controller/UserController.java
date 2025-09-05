package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    // Fetch all user
    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleFetchAllUser(spec, pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> fetchUserById(@PathVariable("id") long id) {

        ResUserDTO userNew = this.userService.handleFetchUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userNew);
    }

    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreUserDTO> createNewUser(@Valid @RequestBody User user) throws IdInvalidException {

        boolean isEmailExist = this.userService.isEmailExist(user.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + user.getEmail() + " đã tồn tại, vui lòng sử dụng email khác");
        }
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        ResCreUserDTO userNew = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
    }

    @PutMapping("/users")
    public ResponseEntity<ResUpUserDTO> updateUser(@RequestBody User user) {

        ResUpUserDTO userNew = this.userService.handleUpdateUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(userNew);
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {

        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
