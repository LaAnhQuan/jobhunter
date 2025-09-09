package vn.hoidanit.jobhunter.mapper;

import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.response.resume.ResCreResumeDTO;

public class ResumeMapper {
    public static ResCreResumeDTO toResCreResumeDTO(Resume resume) {

        return new ResCreResumeDTO(
                resume.getId(),
                resume.getCreatedAt(),
                resume.getCreatedBy());
    }
}
