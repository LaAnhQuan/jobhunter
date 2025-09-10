package vn.hoidanit.jobhunter.service;

import vn.hoidanit.jobhunter.domain.Role;

public interface RoleService {
    boolean existByName(String name);

    Role create(Role r);

    Role fetchById(long id);

    Role update(Role r);

    void delete(long id);
}
