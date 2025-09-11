package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;

public interface RoleService {
    boolean existByName(String name);

    Role create(Role r);

    Role fetchById(long id);

    Role update(Role r);

    void delete(long id);

    ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable);
}