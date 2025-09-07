package vn.hoidanit.jobhunter.mapper;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;

public class UserMapper {

    public static ResCreUserDTO toResCreUserDTO(User user) {
        ResCreUserDTO.CompanyUser com = new ResCreUserDTO.CompanyUser();
        if (user.getCompany() != null) {
            com.setId(user.getCompany().getId());
            com.setName(user.getCompany().getName());
        }

        return new ResCreUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                user.getAddress(),
                user.getAge(),
                user.getCreatedAt(),
                com);
    }

    public static ResUserDTO toResUserDTO(User user) {
        ResUserDTO.CompanyUser com = new ResUserDTO.CompanyUser();
        if (user.getCompany() != null) {
            com.setId(user.getCompany().getId());
            com.setName(user.getCompany().getName());
        }
        return new ResUserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getGender(),
                user.getAddress(),
                user.getAge(),
                user.getUpdatedAt(),
                user.getCreatedAt(),
                com);
    }

    public static ResUpUserDTO toResUpUserDTO(User user) {
        ResUpUserDTO.CompanyUser com = new ResUpUserDTO.CompanyUser();
        if (user.getCompany() != null) {
            com.setId(user.getCompany().getId());
            com.setName(user.getCompany().getName());
        }
        return new ResUpUserDTO(
                user.getId(),
                user.getName(),
                user.getGender(),
                user.getAddress(),
                user.getAge(),
                user.getUpdatedAt(),
                com);
    }
}
