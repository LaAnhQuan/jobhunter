package vn.hoidanit.jobhunter.domain.response.job;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.LevelEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCreJobDTO {
    private long id;
    private String name;
    private String location;
    private double salary;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;
    private Instant startDate;
    private Instant endDate;

    private List<String> skills;

    private Instant createdAt;
    private String createdBy;
    private boolean active;
}
