package vn.hoidanit.jobhunter.mapper;

import java.util.stream.Collectors;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.job.ResCreJobDTO;

public class JobMapper {
        public static ResCreJobDTO toResCreJobDTO(Job job) {
                // ResCreJobDTO.CompanyUser com = new ResCreUserDTO.CompanyUser();
                // if (user.getCompany() != null) {
                // com.setId(user.getCompany().getId());
                // com.setName(user.getCompany().getName());
                // }

                return new ResCreJobDTO(
                                job.getId(),
                                job.getName(),
                                job.getLocation(),
                                job.getSalary(),
                                job.getQuantity(),
                                job.getLevel(),
                                job.getStartDate(),
                                job.getEndDate(),
                                job.getSkills() != null
                                                ? job.getSkills()
                                                                .stream()
                                                                .map(Skill::getName) // lấy tên skill thay vì entity
                                                                .collect(Collectors.toList())
                                                : null,
                                job.getCreatedAt(),
                                job.getCreatedBy(),
                                job.isActive());
        }

}
