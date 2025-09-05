package vn.hoidanit.jobhunter.mapper;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;

public class UserMapper {

    public static ResCreUserDTO toResCreUserDTO(User user) {
        return new ResCreUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getAddress(),
                user.getCreatedAt());
    }

    public static ResUserDTO toResUserDTO(User user) {
        return new ResUserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getGender(),
                user.getAddress(),
                user.getAge(),
                user.getUpdatedAt(),
                user.getCreatedAt());
    }

    public static ResUpUserDTO toResUpUserDTO(User user) {
        return new ResUpUserDTO(
                user.getId(),
                user.getName(),
                user.getGender(),
                user.getAddress(),
                user.getAge(),
                user.getUpdatedAt());
    }
}
