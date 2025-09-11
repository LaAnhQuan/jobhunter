package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Permission;
import vn.hoidanit.jobhunter.domain.Role;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;
import vn.hoidanit.jobhunter.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public boolean existByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    @Override
    public Role create(Role r) {
        // check permissions
        if (r.getPermissions() != null) {
            List<Long> reqPermissions = r.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .collect(Collectors.toList());

            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            r.setPermissions(dbPermissions);
        }

        return this.roleRepository.save(r);
    }

    @Override
    public Role fetchById(long id) {
        return this.roleRepository.findById(id).orElseThrow();
    }

    @Override
    public Role update(Role r) {
        Role roleDB = this.fetchById(r.getId());

        // check permissions
        if (r.getPermissions() != null) {
            List<Long> reqPermissions = r.getPermissions()
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            r.setPermissions(dbPermissions);
        }

        roleDB.setName(r.getName());
        roleDB.setDescription(r.getDescription());
        roleDB.setActive(r.isActive());
        roleDB.setPermissions(r.getPermissions());

        roleDB = this.roleRepository.save(roleDB);
        return roleDB;
    }

    @Override
    public void delete(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getRoles(Specification<Role> spec, Pageable pageable) {
        // Lấy danh sách các Role dựa trên Specification và Pageable
        Page<Role> pRole = this.roleRepository.findAll(spec, pageable);

        // Khởi tạo đối tượng ResultPaginationDto để trả về
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        // Cập nhật thông tin phân trang cho đối tượng Meta
        mt.setPage(pageable.getPageNumber() + 1); // Trang bắt đầu từ 1
        mt.setPageSize(pageable.getPageSize()); // Kích thước mỗi trang
        mt.setPages(pRole.getTotalPages()); // Tổng số trang
        mt.setTotal(pRole.getTotalElements()); // Tổng số phần tử

        // Gán Meta và kết quả cho đối tượng ResultPaginationDto
        rs.setMeta(mt);
        rs.setResult(pRole.getContent()); // Lấy nội dung của trang hiện tại

        // Trả về đối tượng ResultPaginationDto
        return rs;
    }
}
