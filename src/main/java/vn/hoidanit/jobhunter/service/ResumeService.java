package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.Resume;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResCreResumeDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResFetchResumeDTO;
import vn.hoidanit.jobhunter.domain.response.resume.ResUpResumeDTO;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

public interface ResumeService {
    ResCreResumeDTO createResume(Resume resume) throws IdInvalidException;

    ResUpResumeDTO update(Resume resume);

    Optional<Resume> fetchById(long id);

    void delete(long id);

    ResFetchResumeDTO getResume(Resume resume);

    ResultPaginationDTO fetchAllResume(Specification<Resume> spec, Pageable pageable);

    ResultPaginationDTO fetchResumeByUser(Pageable pageable);
}
