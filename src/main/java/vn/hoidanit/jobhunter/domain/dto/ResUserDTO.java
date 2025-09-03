package vn.hoidanit.jobhunter.domain.dto;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResUserDTO {
    private long id;

    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private Instant createdAt;

    public ResUserDTO() {
    }

    public ResUserDTO(long id, String email, String name, GenderEnum gender, String address, int age, Instant updatedAt,
            Instant createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

}
