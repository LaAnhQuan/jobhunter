package vn.hoidanit.jobhunter.domain.response.resume;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpResumeDTO {
    private Instant updatedAt;
    private String updatedBy;
}
