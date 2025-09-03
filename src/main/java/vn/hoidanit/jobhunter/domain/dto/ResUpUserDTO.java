package vn.hoidanit.jobhunter.domain.dto;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResUpUserDTO {
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;

    public ResUpUserDTO() {
    }

    public ResUpUserDTO(long id, String name, GenderEnum gender, String address, int age, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.createdAt = createdAt;
    }

}
