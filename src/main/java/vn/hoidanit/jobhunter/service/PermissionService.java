package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Permission;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;

public interface PermissionService {
    boolean isPermissionExist(Permission p);

    Permission create(Permission p);

    Permission fetchById(long id);

    Permission update(Permission p);

    void delete(long id);

    ResultPaginationDTO getPermissions(Specification<Permission> spec, Pageable pageable);
}
