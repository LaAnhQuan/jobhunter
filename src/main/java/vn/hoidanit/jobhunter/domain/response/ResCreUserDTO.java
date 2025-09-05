package vn.hoidanit.jobhunter.domain.response;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter

public class ResCreUserDTO {
    private long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;

    public ResCreUserDTO(long l, String string, GenderEnum genderEnum, String string2, int i, Instant instant) {
    }

    public ResCreUserDTO(long id, String name, String email, int age, GenderEnum gender, String address,
            Instant createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.createdAt = createdAt;
    }

}
